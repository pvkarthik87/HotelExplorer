package com.karcompany.hotelexplorer.di.modules;

import android.content.Context;

import com.karcompany.hotelexplorer.HotelExplorerApplication;
import com.karcompany.hotelexplorer.networking.ApiRepo;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenter;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenterImpl;

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
}
