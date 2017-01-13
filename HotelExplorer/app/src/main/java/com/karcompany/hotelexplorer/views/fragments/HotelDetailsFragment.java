package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Cars fragment which displays server data in a recycler view.
 */

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Summary;
import com.karcompany.hotelexplorer.presenters.HotelDetailsPresenter;
import com.karcompany.hotelexplorer.views.HotelDetailsView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;

public class HotelDetailsFragment extends BaseFragment implements HotelDetailsView {

	private static final String TAG = DefaultLogger.makeLogTag(HotelDetailsFragment.class);

	@Inject
	HotelDetailsPresenter mHotelDetailsPresenter;

	@Bind(R.id.hotelLowRate)
	TextView mHotelLowPriceView;

	@Bind(R.id.hotelHighRate)
	TextView mHotelHighPriceView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hotel_details	, container, false);
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
		mHotelDetailsPresenter.setView(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mHotelDetailsPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mHotelDetailsPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mHotelDetailsPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mHotelDetailsPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHotelDetailsPresenter.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void updateHotelDetails(Hotel hotel) {
		if(hotel != null) {
			Summary summary = hotel.getSummary();
			if(summary != null) {
				mHotelLowPriceView.setText(String.format(Locale.getDefault(), getString(R.string.price_format), summary.getLowRate()));
				mHotelHighPriceView.setText(String.format(Locale.getDefault(), getString(R.string.price_format), summary.getHighRate()));
				mHotelHighPriceView.setPaintFlags(mHotelHighPriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			}
		}
	}
}
