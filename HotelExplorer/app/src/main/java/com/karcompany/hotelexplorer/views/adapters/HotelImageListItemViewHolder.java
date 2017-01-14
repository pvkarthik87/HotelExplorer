package com.karcompany.hotelexplorer.views.adapters;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.hotelexplorer.R;

public class HotelImageListItemViewHolder extends RecyclerView.ViewHolder {

	public ImageView hotelImgView;

	public HotelImageListItemViewHolder(View itemView) {
		super(itemView);
		hotelImgView = (ImageView) itemView.findViewById(R.id.hotelImage);
	}

}
