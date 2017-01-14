package com.karcompany.hotelexplorer.views.activities;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Shows detailed info about a hotel.
 */

import android.os.Bundle;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.HasComponent;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.events.BusEvents;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.models.Image;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class HotelDetailActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_detail);
		setTitle(getString(R.string.hotel_details));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
