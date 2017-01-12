package com.karcompany.hotelexplorer.views.adapters;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.hotelexplorer.R;

public class HotelListItemViewHolder extends RecyclerView.ViewHolder {

	public TextView hotelNameTxtView;
	public ImageView hotelImgView;

	public HotelListItemViewHolder(View itemView) {
		super(itemView);
		hotelNameTxtView = (TextView) itemView.findViewById(R.id.hotelName);
		hotelImgView = (ImageView) itemView.findViewById(R.id.hotelImage);
	}

}
