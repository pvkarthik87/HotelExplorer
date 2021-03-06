package com.karcompany.hotelexplorer.presenters;

import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.mvputils.Presenter;
import com.karcompany.hotelexplorer.views.BrowseHotelsView;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter interface which helps in getting in hotels from server.
 *
 */

public interface BrowseHotelsPresenter extends Presenter {

	void setView(BrowseHotelsView browseHotelsView);

	void onHotelSelected(Hotel hotel);

	Hotel getSelectedHotel();
}
