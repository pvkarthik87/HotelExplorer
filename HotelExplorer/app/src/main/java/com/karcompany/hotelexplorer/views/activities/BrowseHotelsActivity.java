package com.karcompany.hotelexplorer.views.activities;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Initial launch activity which will get displayed on first launch of application.
 * Displays list of hotels.
 *
 */

import android.os.Bundle;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.di.HasComponent;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;

public class BrowseHotelsActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_hotels);
		setTitle(getString(R.string.app_name));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
