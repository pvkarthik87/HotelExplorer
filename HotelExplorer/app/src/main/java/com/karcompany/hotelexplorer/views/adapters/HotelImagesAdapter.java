package com.karcompany.hotelexplorer.views.adapters;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Recycler view adapter which displays hotel image data.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.karcompany.hotelexplorer.HotelExplorerApplication;
import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.events.BusEvents;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.models.Image;
import com.karcompany.hotelexplorer.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HotelImagesAdapter extends RecyclerView.Adapter<HotelImageListItemViewHolder> {

	private Fragment mFragment;
	private Context mContext;
	private List<Image> mHotelImageList;
	private boolean mIsFullScreen;

	@Inject
	RxBus mEventBus;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			Image hotelImage = mHotelImageList.get(itemPosition);
			BusEvents.HotelImageClickedEvent event = new BusEvents.HotelImageClickedEvent();
			event.hotelImage = hotelImage;
			mEventBus.send(event);
		}
	};

	public HotelImagesAdapter(Fragment fragment) {
		HotelExplorerApplication.getApplicationComponent().inject(this);
		mFragment = fragment;
		mContext = fragment.getContext();
		mHotelImageList = new ArrayList<>(4);
	}

	@Override
	public HotelImageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if(!mIsFullScreen) {
			view = LayoutInflater.from(mContext).inflate(R.layout.view_hotel_image_row_item, parent, false);
		} else {
			view = LayoutInflater.from(mContext).inflate(R.layout.view_hotel_image_row_item_fullscreen, parent, false);
		}
		return new HotelImageListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(HotelImageListItemViewHolder holder, int position) {
		Glide.clear(holder.hotelImgView);
		holder.hotelImgView.setImageDrawable(null);
		if(position < mHotelImageList.size()) {
			Image hotelImage = mHotelImageList.get(position);
			if(hotelImage != null) {
				GlideUtils.loadImage(mFragment, hotelImage.getUrl(), holder.hotelImgView);
			}
			holder.itemView.setTag(position);
			holder.itemView.setOnClickListener(mOnClickListener);
		}
	}

	@Override
	public void onViewRecycled(HotelImageListItemViewHolder holder) {
		Glide.clear(holder.hotelImgView);
		holder.hotelImgView.setImageDrawable(null);
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		return mHotelImageList.size();
	}

	public void addImages(List<Image> hotelImages) {
		clearData();
		if(hotelImages != null) {
			mHotelImageList.addAll(hotelImages);
		}
		notifyDataSetChanged();
	}

	public void clearData() {
		mHotelImageList.clear();
	}

	public void setFullScreenMode(boolean mode) {
		mIsFullScreen = mode;
	}
}
