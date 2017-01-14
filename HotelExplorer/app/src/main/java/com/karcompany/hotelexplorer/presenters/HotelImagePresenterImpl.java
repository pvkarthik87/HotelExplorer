package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Image;
import com.karcompany.hotelexplorer.views.HotelImageView;

import javax.inject.Inject;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features.
 */

public class HotelImagePresenterImpl implements HotelImagePresenter {

	private HotelImageView mView;
	private BrowseHotelsPresenter mBrowseHotelsPresenter;
	private Image mSelectedHotelImage;

	@Inject
	public HotelImagePresenterImpl(BrowseHotelsPresenter browseHotelsPresenter) {
		mBrowseHotelsPresenter = browseHotelsPresenter;
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


	private void loadImages() {
		Hotel hotel = mBrowseHotelsPresenter.getSelectedHotel();
		if(hotel != null) {
			if(mView != null) {
				mView.updateHotelImages(hotel);
			}
		}
	}

	@Override
	public void onHotelImageSelected(Image hotelImage) {
		mSelectedHotelImage = hotelImage;
	}
}
