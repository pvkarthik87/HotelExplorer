package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.networking.ApiRepo;
import com.karcompany.hotelexplorer.networking.NetworkError;
import com.karcompany.hotelexplorer.views.BrowseHotelsView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features (fetches hotels from server).
 */

public class BrowseHotelsPresenterImpl implements BrowseHotelsPresenter, ApiRepo.GetHotelsApiCallback {

	private BrowseHotelsView mView;
	private Hotel mSelectedHotel;

	@Inject
	ApiRepo mApiRepo;

	private boolean mIsLoading;
	private CompositeSubscription subscriptions;

	@Inject
	public BrowseHotelsPresenterImpl(ApiRepo apiRepo) {
		mApiRepo = apiRepo;
		this.subscriptions = new CompositeSubscription();
	}

	@Override
	public void setView(BrowseHotelsView browseUsersView) {
		mView = browseUsersView;
		subscriptions = new CompositeSubscription();
	}

	@Override
	public void onStart() {
		loadHotels();
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
		if(subscriptions != null) {
			subscriptions.unsubscribe();
		}
	}

	private void loadHotels() {
		mIsLoading = true;
		if(mView != null) {
			mView.onLoadStateChanged(mIsLoading);
		}
		Subscription subscription = mApiRepo.getHotels(this);
		subscriptions.add(subscription);
	}

	@Override
	public void onSuccess(GetHotelsApiResponse response) {
		mIsLoading = false;
		if (mView != null) {
			mView.onLoadStateChanged(mIsLoading);
			mView.onDataReceived(response);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onLoadStateChanged(mIsLoading);
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}

	@Override
	public void onHotelSelected(Hotel hotel) {
		mSelectedHotel = hotel;
	}

	@Override
	public Hotel getSelectedHotel() {
		return mSelectedHotel;
	}
}
