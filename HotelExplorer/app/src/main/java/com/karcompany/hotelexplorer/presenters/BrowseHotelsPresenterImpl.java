package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;
import com.karcompany.hotelexplorer.networking.ApiRepo;
import com.karcompany.hotelexplorer.networking.NetworkError;
import com.karcompany.hotelexplorer.views.BrowseHotelsView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Presenter implementation which handles core features.
 */

public class BrowseHotelsPresenterImpl implements BrowseHotelsPresenter, ApiRepo.GetHotelsApiCallback {

	private BrowseHotelsView mView;

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

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	private void loadHotels() {
		mIsLoading = true;
		Subscription subscription = mApiRepo.getHotels(this);
		subscriptions.add(subscription);
	}

	@Override
	public void onSuccess(GetHotelsApiResponse response) {
		mIsLoading = false;
		if (mView != null) {
			mView.onDataReceived(response);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}
}
