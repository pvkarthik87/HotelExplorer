package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.mvputils.Presenter;
import com.karcompany.hotelexplorer.views.HotelDetailedView;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter interface.
 *
 */

public interface HotelDetailedPresenter extends Presenter {

	void setView(HotelDetailedView hotelDetailedView);

	boolean isLoading();

}
