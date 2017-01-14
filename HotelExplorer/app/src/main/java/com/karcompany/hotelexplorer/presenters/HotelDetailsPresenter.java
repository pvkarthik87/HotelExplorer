package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.mvputils.Presenter;
import com.karcompany.hotelexplorer.views.HotelDetailsView;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter interface.
 *
 */

public interface HotelDetailsPresenter extends Presenter {

	void setView(HotelDetailsView hotelDetailsView);

}
