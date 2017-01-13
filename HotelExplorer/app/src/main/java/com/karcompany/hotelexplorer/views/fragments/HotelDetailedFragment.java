package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Location;
import com.karcompany.hotelexplorer.models.Summary;
import com.karcompany.hotelexplorer.presenters.HotelDetailedPresenter;
import com.karcompany.hotelexplorer.views.HotelDetailedView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class HotelDetailedFragment extends BaseFragment implements HotelDetailedView {

	private static final String TAG = DefaultLogger.makeLogTag(HotelDetailedFragment.class);

	@Inject
	HotelDetailedPresenter mHotelDetailedPresenter;

	@Bind(R.id.hotelName)
	TextView mHotelNameView;

	@Bind(R.id.hotelAddress)
	TextView mHotelAddressView;

	private Hotel mCurrentHotel;

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

	@Override
	public void updateHotelDetails(Hotel hotel) {
		if(hotel != null) {
			mCurrentHotel = hotel;
			Summary summary = hotel.getSummary();
			if(summary != null) {
				if(!TextUtils.isEmpty(summary.getHotelName())) {
					mHotelNameView.setText(summary.getHotelName());
				}
			}
			Location location = hotel.getLocation();
			if(location != null) {
				if(!TextUtils.isEmpty(location.getAddress())) {
					mHotelAddressView.setText(location.getAddress());
				}
			}
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
}
