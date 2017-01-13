package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenter;
import com.karcompany.hotelexplorer.presenters.HotelImagePresenter;
import com.karcompany.hotelexplorer.views.HotelImageView;

import javax.inject.Inject;

import butterknife.Bind;

public class HotelImagesFragment extends BaseFragment implements HotelImageView {

	private static final String TAG = DefaultLogger.makeLogTag(HotelImagesFragment.class);

	@Inject
	BrowseHotelsPresenter mBrowseHotelsPresenter;

	@Inject
	HotelImagePresenter mHotelImagePresenter;

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
	}

	private void setUpPresenter() {
		mHotelImagePresenter.setView(this);
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
	}

	@Override
	public void onPause() {
		super.onPause();
		mHotelImagePresenter.onPause();
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
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
