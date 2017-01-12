package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.mvputils.Presenter;
import com.karcompany.hotelexplorer.views.BrowseHotelsView;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Presenter interface.
 *
 */

public interface BrowseHotelsPresenter extends Presenter {

	void setView(BrowseHotelsView browseHotelsView);

	boolean isLoading();
}