package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.views.HotelDetailsView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features (displays detailed hotel info).
 */

public class HotelDetailsPresenterImpl implements HotelDetailsPresenter {

	private HotelDetailsView mView;
	private BrowseHotelsPresenter mBrowseHotelsPresenter;

	@Inject
	public HotelDetailsPresenterImpl(BrowseHotelsPresenter browseHotelsPresenter) {
		mBrowseHotelsPresenter = browseHotelsPresenter;
	}

	@Override
	public void setView(HotelDetailsView hotelDetailsView) {
		mView = hotelDetailsView;
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

	private void loadHotelDetails() {
		Hotel hotel = mBrowseHotelsPresenter.getSelectedHotel();
		if(hotel != null) {
			if(mView != null) {
				mView.updateHotelDetails(hotel);
			}
		}
	}
}
