package com.karcompany.hotelexplorer.di.components;

import android.content.Context;

import com.karcompany.hotelexplorer.HotelExplorerApplication;
import com.karcompany.hotelexplorer.di.modules.ApplicationModule;
import com.karcompany.hotelexplorer.networking.NetworkModule;
import com.karcompany.hotelexplorer.views.activities.BaseActivity;
import com.karcompany.hotelexplorer.views.activities.HotelDetailActivity;
import com.karcompany.hotelexplorer.views.activities.ImageFullScreenActivity;
import com.karcompany.hotelexplorer.views.adapters.BrowseHotelsAdapter;
import com.karcompany.hotelexplorer.views.adapters.HotelImagesAdapter;
import com.karcompany.hotelexplorer.views.fragments.BrowseHotelsFragment;
import com.karcompany.hotelexplorer.views.fragments.HotelDetailsFragment;
import com.karcompany.hotelexplorer.views.fragments.HotelImagesFragment;

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

	void inject(BrowseHotelsFragment browseHotelsFragment);

	void inject(BrowseHotelsAdapter browseHotelsAdapter);

	void inject(HotelImagesFragment hotelImagesFragment);

	void inject(HotelDetailsFragment hotelDetailsFragment);

	void inject(HotelImagesAdapter hotelImagesAdapter);

	void inject(HotelDetailActivity hotelDetailActivity);

	void inject(ImageFullScreenActivity imageFullScreenActivity);
	
	//Exposed to sub-graphs.
	Context context();
}
