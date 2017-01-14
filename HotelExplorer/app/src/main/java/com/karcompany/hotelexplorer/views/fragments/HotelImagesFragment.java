package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * displays hotel images in fullscreen mode.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.config.ViewType;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.events.BusEvents;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Image;
import com.karcompany.hotelexplorer.presenters.HotelImagePresenter;
import com.karcompany.hotelexplorer.views.HotelImageView;
import com.karcompany.hotelexplorer.views.activities.HotelDetailActivity;
import com.karcompany.hotelexplorer.views.activities.ImageFullScreenActivity;
import com.karcompany.hotelexplorer.views.adapters.HotelImagesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class HotelImagesFragment extends BaseFragment implements HotelImageView {

	private static final String TAG = DefaultLogger.makeLogTag(HotelImagesFragment.class);

	@Inject
	HotelImagePresenter mHotelImagePresenter;

	@Bind(R.id.hotel_image_list)
	RecyclerView mHotelImagesRecyclerView;

	private HotelImagesAdapter mAdapter;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	private LinearLayoutManager mLayoutManager;
	private Hotel mCurrentHotel;

	@Bind(R.id.hotelImagesView)
	RelativeLayout mHotelImagesLyt;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hotel_images, container, false);
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
		mHotelImagePresenter.setView(this);
	}

	private void setUpRecyclerView() {
		mLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.HORIZONTAL, false);
		mHotelImagesRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new HotelImagesAdapter(this);
		mAdapter.setFullScreenMode(true);
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
		mHotelImagePresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mHotelImagePresenter.onResume();
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
			onHotelImageClicked();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mHotelImagePresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mHotelImagePresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHotelImagePresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void updateHotelImages(Hotel hotel) {
		if(hotel != null) {
			mCurrentHotel = hotel;
			List<Image> hotelImages = hotel.getImage();
			mAdapter.addImages(hotelImages);
		}
	}

	@OnClick(R.id.fullScreenBtn)
	public void onFullScreenBtnClicked() {
		onHotelImageClicked();
	}

	private void onHotelImageClicked() {
		getActivity().finish();
	}
}
