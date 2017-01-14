package com.karcompany.hotelexplorer.views.adapters;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Recycler view adapter which displays data.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.karcompany.hotelexplorer.HotelExplorerApplication;
import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.config.ViewType;
import com.karcompany.hotelexplorer.events.BusEvents;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Image;
import com.karcompany.hotelexplorer.models.Summary;
import com.karcompany.hotelexplorer.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public class BrowseHotelsAdapter extends RecyclerView.Adapter<HotelListItemViewHolder> {

	private Fragment mFragment;
	private Context mContext;
	private Map<Long, Hotel> mHotelDataMap;
	private List<Long> mHotelIdList;
	private ViewType mCurrentViewType;
	private Random mRandom = new Random();

	@Inject
	RxBus mEventBus;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			Long item = mHotelIdList.get(itemPosition);
			BusEvents.HotelClickedEvent event =  new BusEvents.HotelClickedEvent();
			event.hotel = mHotelDataMap.get(item);
			mEventBus.send(event);
		}
	};

	public BrowseHotelsAdapter(Fragment fragment) {
		HotelExplorerApplication.getApplicationComponent().inject(this);
		mFragment = fragment;
		mContext = fragment.getContext();
		mHotelDataMap = new LinkedHashMap<>();
		mHotelIdList = new ArrayList<>(4);
	}

	@Override
	public HotelListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		switch (mCurrentViewType) {
			case LIST: {
				view = LayoutInflater.from(mContext).inflate(R.layout.view_hotel_row_item_list, parent, false);
			}
			break;

			case GRID: {
				view = LayoutInflater.from(mContext).inflate(R.layout.view_hotel_row_item_grid, parent, false);
			}
			break;

			case STAGGERED: {
				view = LayoutInflater.from(mContext).inflate(R.layout.view_hotel_row_item_staggered, parent, false);
			}
			break;
		}
		return new HotelListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(HotelListItemViewHolder holder, int position) {
		if(mCurrentViewType == ViewType.STAGGERED) {
			holder.hotelImgView.getLayoutParams().height = getRandomIntInRange(300,200);
		}
		holder.hotelNameTxtView.setText("");
		Glide.clear(holder.hotelImgView);
		holder.hotelImgView.setImageDrawable(null);
		if(position < mHotelIdList.size()) {
			Hotel hotel = mHotelDataMap.get(mHotelIdList.get(position));
			if(hotel != null) {
				Summary summary = hotel.getSummary();
				if(summary != null && !TextUtils.isEmpty(summary.getHotelName())) {
					holder.hotelNameTxtView.setText(summary.getHotelName());
				}
				List<Image> hotelImages = hotel.getImage();
				if(hotelImages != null && hotelImages.size() > 0) {
					GlideUtils.loadImage(mFragment, hotelImages.get(0).getUrl(), holder.hotelImgView);
				}
			}
			holder.itemView.setTag(position);
			holder.itemView.setOnClickListener(mOnClickListener);
		}
	}

	// Custom method to get a random number between a range
	protected int getRandomIntInRange(int max, int min){
		return mRandom.nextInt((max-min)+min)+min;
	}

	@Override
	public void onViewRecycled(HotelListItemViewHolder holder) {
		Glide.clear(holder.hotelImgView);
		holder.hotelImgView.setImageDrawable(null);
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		return mHotelIdList.size();
	}

	public void addData(GetHotelsApiResponse response) {
		if(response != null && response.getHotel() != null) {
			for (Hotel hotel:response.getHotel()) {
				mHotelDataMap.put(hotel.getHotelId(), hotel);
			}
			int oldSize = mHotelIdList.size();
			mHotelIdList.clear();
			mHotelIdList.addAll(mHotelDataMap.keySet());
			int newSize = mHotelIdList.size();
			if(oldSize > 0) {
				notifyItemRangeInserted(oldSize, newSize - oldSize);
			} else {
				notifyDataSetChanged();
			}
		}
	}

	public void addData(ArrayList<Hotel> hotelArrayList) {
		if(hotelArrayList != null) {
			for (Hotel hotel:hotelArrayList) {
				mHotelDataMap.put(hotel.getHotelId(), hotel);
			}
			int oldSize = mHotelIdList.size();
			mHotelIdList.clear();
			mHotelIdList.addAll(mHotelDataMap.keySet());
			int newSize = mHotelIdList.size();
			if(oldSize > 0) {
				notifyItemRangeInserted(oldSize, newSize - oldSize);
			} else {
				notifyDataSetChanged();
			}
		}
	}

	public void clearData() {
		mHotelIdList.clear();
		mHotelDataMap.clear();
	}

	public void setViewMode(ViewType viewType) {
		mCurrentViewType = viewType;
	}

	public ArrayList<Hotel> getHotelList() {
		ArrayList<Hotel> hotelArrayList = new ArrayList<>();
		Iterator<Map.Entry<Long, Hotel>> iterator = mHotelDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Long, Hotel> entry = iterator.next();
			hotelArrayList.add(entry.getValue());
		}
		return hotelArrayList;
	}
}
