package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.events.BusEvents;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Image;
import com.karcompany.hotelexplorer.models.Location;
import com.karcompany.hotelexplorer.models.Summary;
import com.karcompany.hotelexplorer.presenters.HotelDetailsPresenter;
import com.karcompany.hotelexplorer.views.HotelDetailsView;
import com.karcompany.hotelexplorer.views.activities.HotelDetailActivity;
import com.karcompany.hotelexplorer.views.activities.ImageFullScreenActivity;
import com.karcompany.hotelexplorer.views.adapters.HotelImagesAdapter;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class HotelDetailsFragment extends BaseFragment implements HotelDetailsView {

	private static final String TAG = DefaultLogger.makeLogTag(HotelDetailsFragment.class);

	@Inject
	HotelDetailsPresenter mHotelDetailsPresenter;

	@Bind(R.id.hotelLowRate)
	TextView mHotelLowPriceView;

	@Bind(R.id.hotelHighRate)
	TextView mHotelHighPriceView;

	@Bind(R.id.hotelName)
	TextView mHotelNameView;

	@Bind(R.id.hotelAddress)
	TextView mHotelAddressView;

	private Hotel mCurrentHotel;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	@Bind(R.id.hotelImagesLyt)
	RelativeLayout mHotelImagesLyt;

	@Bind(R.id.hotelDetailsLyt)
	LinearLayout mHotelDetailsLyt;

	@Bind(R.id.hotel_image_list)
	RecyclerView mHotelImagesRecyclerView;

	private HotelImagesAdapter mAdapter;

	private LinearLayoutManager mLayoutManager;

	@Bind(R.id.hotelPriceBar)
	View mHotelPriceBar;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hotel_details	, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
		setUpRecyclerView();
	}

	private void setUpPresenter() {
		mHotelDetailsPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		mLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.HORIZONTAL, false);
		mHotelImagesRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new HotelImagesAdapter(this);
		mHotelImagesRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mHotelDetailsPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mHotelDetailsPresenter.onResume();
		autoUnsubBus();
		subscribeBus();
	}

	private void subscribeBus() {
		mBusSubscription = mEventBus.toObserverable()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new Action1<Object>() {
							@Override
							public void call(Object o) {
								handlerBus(o);
							}
						}
				);
	}

	private void autoUnsubBus() {
		if (mBusSubscription != null && !mBusSubscription.isUnsubscribed()) {
			mBusSubscription.unsubscribe();
		}
	}

	private void handlerBus(Object o) {
		if (o instanceof BusEvents.HotelImageClickedEvent) {
			BusEvents.HotelImageClickedEvent event = (BusEvents.HotelImageClickedEvent) o;
			onHotelImageClicked(event.hotelImage);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mHotelDetailsPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mHotelDetailsPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHotelDetailsPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mHotelImagesRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void updateHotelDetails(Hotel hotel) {
		if(hotel != null) {
			mCurrentHotel = hotel;
			Summary summary = hotel.getSummary();
			if(summary != null) {
				if(!TextUtils.isEmpty(summary.getHotelName())) {
					mHotelNameView.setText(summary.getHotelName());
				}
				mHotelLowPriceView.setText(String.format(Locale.getDefault(), getString(R.string.price_format), summary.getLowRate()));
				mHotelHighPriceView.setText(String.format(Locale.getDefault(), getString(R.string.price_format), summary.getHighRate()));
				mHotelHighPriceView.setPaintFlags(mHotelHighPriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			}
			Location location = hotel.getLocation();
			if(location != null) {
				if(!TextUtils.isEmpty(location.getAddress())) {
					mHotelAddressView.setText(location.getAddress());
				}
			}
			List<Image> hotelImages = hotel.getImage();
			mAdapter.addImages(hotelImages);
		}
	}

	@OnClick(R.id.mapView)
	public void onMapViewClicked() {
		if(mCurrentHotel != null) {
			Location location = mCurrentHotel.getLocation();
			Summary summary = mCurrentHotel.getSummary();
			if(location != null && summary != null && !TextUtils.isEmpty(summary.getHotelName())) {
				String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(%s)", location.getLatitude(), location.getLongitude(), location.getLatitude(), location.getLongitude(), summary.getHotelName());
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		}
	}

	private void onHotelImageClicked(Image hotelImage) {
		Intent intent = new Intent(getActivity(), ImageFullScreenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@OnClick(R.id.fullScreenBtn)
	public void onFullScreenBtnClicked() {
		if(mCurrentHotel != null) {
			int clickedImagePos = mLayoutManager.findFirstVisibleItemPosition();
			List<Image> hotelImages = mCurrentHotel.getImage();
			if(hotelImages != null && clickedImagePos < hotelImages.size()) {
				Image hotelImage = hotelImages.get(clickedImagePos);
				BusEvents.HotelImageClickedEvent event =  new BusEvents.HotelImageClickedEvent();
				event.hotelImage = hotelImage;
				mEventBus.send(event);
			}
		}
	}
}
