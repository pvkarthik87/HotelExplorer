package com.karcompany.hotelexplorer.events;

import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.models.Image;

/**
 * Created by pvkarthik on 2017-01-12.
 */

public class BusEvents {

	public static class HotelClickedEvent {

		public Hotel hotel;
	}

	public static class HotelImageClickedEvent {

		public Image hotelImage;
	}

}
