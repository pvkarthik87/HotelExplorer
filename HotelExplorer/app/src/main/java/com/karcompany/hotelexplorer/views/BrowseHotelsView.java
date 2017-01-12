package com.karcompany.hotelexplorer.views;

import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;
import com.karcompany.hotelexplorer.models.Hotel;

import java.util.List;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface BrowseHotelsView {

	void onDataReceived(GetHotelsApiResponse response);

	void onFailure(String errorMsg);

}
