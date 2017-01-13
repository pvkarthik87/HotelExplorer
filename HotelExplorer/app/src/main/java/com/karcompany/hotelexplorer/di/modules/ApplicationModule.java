package com.karcompany.hotelexplorer.di.modules;

import android.content.Context;

import com.karcompany.hotelexplorer.HotelExplorerApplication;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.networking.ApiRepo;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenter;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenterImpl;
import com.karcompany.hotelexplorer.presenters.HotelDetailedPresenter;
import com.karcompany.hotelexplorer.presenters.HotelDetailedPresenterImpl;
import com.karcompany.hotelexplorer.presenters.HotelDetailsPresenter;
import com.karcompany.hotelexplorer.presenters.HotelDetailsPresenterImpl;
import com.karcompany.hotelexplorer.presenters.HotelImagePresenter;
import com.karcompany.hotelexplorer.presenters.HotelImagePresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
	private final HotelExplorerApplication application;

	public ApplicationModule(HotelExplorerApplication application) {
		this.application = application;
	}

	@Provides @Singleton
	Context provideApplicationContext() {
		return this.application;
	}

	@Provides @Singleton
	BrowseHotelsPresenter provideBrowseHotelsPresenter(ApiRepo apiRepo) {
		return new BrowseHotelsPresenterImpl(apiRepo);
	}

	@Provides @Singleton
	RxBus provideRxBus() {
		return new RxBus();
	}

	@Provides @Singleton
	HotelImagePresenter provideHotelImagePresenter(BrowseHotelsPresenter browseHotelsPresenter) {
		return new HotelImagePresenterImpl(browseHotelsPresenter);
	}

	@Provides @Singleton
	HotelDetailedPresenter provideHotelDetailedPresenter(BrowseHotelsPresenter browseHotelsPresenter) {
		return new HotelDetailedPresenterImpl(browseHotelsPresenter);
	}

	@Provides @Singleton
	HotelDetailsPresenter provideHotelDetailsPresenter(BrowseHotelsPresenter browseHotelsPresenter) {
		return new HotelDetailsPresenterImpl(browseHotelsPresenter);
	}
}
