package com.karcompany.hotelexplorer.di.components;

import android.content.Context;

import com.karcompany.hotelexplorer.HotelExplorerApplication;
import com.karcompany.hotelexplorer.di.modules.ApplicationModule;
import com.karcompany.hotelexplorer.networking.NetworkModule;
import com.karcompany.hotelexplorer.views.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

	void inject(HotelExplorerApplication hotelExplorerApplication);

	void inject(BaseActivity baseActivity);
	
	//Exposed to sub-graphs.
	Context context();
}
