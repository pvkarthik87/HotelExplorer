package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.views.HotelImageView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features.
 */

public class HotelImagePresenterImpl implements HotelImagePresenter {

	private HotelImageView mView;
	private boolean mIsLoading;
	private BrowseHotelsPresenter mBrowseHotelsPresenter;

	@Inject
	public HotelImagePresenterImpl(BrowseHotelsPresenter browseHotelsPresenter) {

	}

	@Override
	public void setView(HotelImageView hotelImageView) {
		mView = hotelImageView;
	}

	@Override
	public void onStart() {
		loadImages();
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

	private void loadImages() {
		mIsLoading = true;
	}
}
