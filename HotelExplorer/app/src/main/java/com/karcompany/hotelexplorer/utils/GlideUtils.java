package com.karcompany.hotelexplorer.utils;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.logging.DefaultLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Used to load images.
 */
public class GlideUtils {

	public static String TAG = DefaultLogger.makeLogTag(GlideUtils.class);
	private static int mColorIndex = 0;

	public static final List<Integer> mColourList;

	static {
		List<Integer> colourList = new ArrayList<>(4);
		colourList.add(R.color.accent_brand);
		colourList.add(R.color.neutral_medium_soft_dark);
		colourList.add(R.color.light_brand);
		colourList.add(R.color.medium_brand);
		mColourList = Collections.unmodifiableList(colourList);
	}

	public static void loadImage(Fragment fragment, String url, ImageView gifView) {
		if (TextUtils.isEmpty(url)) return;
		Glide
				.with(fragment)
				.load(url)
				.asBitmap()
				.placeholder(getColorIndex())
				.error(getColorIndex())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(gifView);
	}

	private static int getColorIndex() {
		int colour = mColourList.get(mColorIndex);
		mColorIndex++;
		if (mColorIndex == mColourList.size()) mColorIndex = 0;
		return colour;
	}

}
