package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.mvputils.Presenter;
import com.karcompany.hotelexplorer.views.HotelImageView;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter interface.
 *
 */

public interface HotelImagePresenter extends Presenter {

	void setView(HotelImageView hotelImageView);

	boolean isLoading();

}
