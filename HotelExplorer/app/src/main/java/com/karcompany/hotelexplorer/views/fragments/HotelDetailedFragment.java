package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.presenters.HotelDetailedPresenter;
import com.karcompany.hotelexplorer.views.HotelDetailedView;

import javax.inject.Inject;

import butterknife.Bind;

public class HotelDetailedFragment extends BaseFragment implements HotelDetailedView {

	private static final String TAG = DefaultLogger.makeLogTag(HotelDetailedFragment.class);

	@Inject
	HotelDetailedPresenter mHotelDetailedPresenter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hotel_detailed_info, container, false);
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
		mHotelDetailedPresenter.setView(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mHotelDetailedPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mHotelDetailedPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mHotelDetailedPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mHotelDetailedPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHotelDetailedPresenter.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
