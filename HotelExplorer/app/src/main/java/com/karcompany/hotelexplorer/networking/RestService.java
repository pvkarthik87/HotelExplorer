package com.karcompany.hotelexplorer.networking;

import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * REST service interface which Retrofit uses to communicate to a rest end point.
 */

public interface RestService {

	@GET("/tajawal/hotels_exercise.json")
	Observable<GetHotelsApiResponse> getHotels();

}