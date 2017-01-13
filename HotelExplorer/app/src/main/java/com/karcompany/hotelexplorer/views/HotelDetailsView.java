package com.karcompany.hotelexplorer.views;

import com.karcompany.hotelexplorer.models.Hotel;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface HotelDetailsView {

	void updateHotelDetails(Hotel hotel);

}
