package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.views.HotelDetailedView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features.
 */

public class HotelDetailedPresenterImpl implements HotelDetailedPresenter {

	private HotelDetailedView mView;
	private boolean mIsLoading;

	@Inject
	public HotelDetailedPresenterImpl() {
	}

	@Override
	public void setView(HotelDetailedView hotelDetailedView) {
		mView = hotelDetailedView;
	}

	@Override
	public void onStart() {
		loadHotelDetails();
	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	private void loadHotelDetails() {
		mIsLoading = true;
	}
}
