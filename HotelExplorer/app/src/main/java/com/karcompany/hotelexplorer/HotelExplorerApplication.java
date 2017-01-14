package com.karcompany.hotelexplorer;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Android Application.
 */

import android.app.Application;

import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.di.components.DaggerApplicationComponent;
import com.karcompany.hotelexplorer.di.modules.ApplicationModule;
import com.karcompany.hotelexplorer.networking.NetworkModule;

public class HotelExplorerApplication extends Application {

	private static ApplicationComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeInjector();
	}

	private void initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.networkModule(new NetworkModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public static ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
}

