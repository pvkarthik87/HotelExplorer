package com.karcompany.hotelexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Hotels fragment which displays server data in a recycler view.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.hotelexplorer.R;
import com.karcompany.hotelexplorer.config.ViewType;
import com.karcompany.hotelexplorer.di.components.ApplicationComponent;
import com.karcompany.hotelexplorer.events.BusEvents;
import com.karcompany.hotelexplorer.events.RxBus;
import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenter;
import com.karcompany.hotelexplorer.views.BrowseHotelsView;
import com.karcompany.hotelexplorer.views.activities.HotelDetailActivity;
import com.karcompany.hotelexplorer.views.adapters.BrowseHotelsAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BrowseHotelsFragment extends BaseFragment implements BrowseHotelsView {

	private static final String TAG = DefaultLogger.makeLogTag(BrowseHotelsFragment.class);

	private static final int REQUEST_CODE_DETAIL_HOTEL = 0x026;

	private static final String CURRENT_VIEW_TYPE = "CURRENT_VIEW_TYPE";
	private static final String CURRENT_HOTEL_LIST = "CURRENT_HOTEL_LIST";

	@Bind(R.id.hotel_list)
	RecyclerView mHotelsRecyclerView;

	@Inject
	BrowseHotelsPresenter mBrowseHotelsPresenter;

	@Bind(R.id.fabbutton)
	FloatingActionButton mFabBtn;

	private BrowseHotelsAdapter mAdapter;
	private ViewType mCurrentViewType = ViewType.LIST;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;

	private boolean mIsComingBack;

	@Bind(R.id.progressView)
	View mLoadProgressView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_browse_hotels, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_VIEW_TYPE)) {
				mCurrentViewType = ViewType.get(savedInstanceState.getString(CURRENT_VIEW_TYPE, ViewType.LIST.toString()));
			}
		}
		setUpPresenter();
		setUpFabBtn();
		mAdapter = new BrowseHotelsAdapter(this);
		setUpRecyclerView();
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_HOTEL_LIST)) {
				ArrayList<Hotel> hotelArrayList = savedInstanceState.getParcelableArrayList(CURRENT_HOTEL_LIST);
				mAdapter.addData(hotelArrayList);
			}
		}
	}

	private void setUpPresenter() {
		mBrowseHotelsPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		switch(mCurrentViewType) {
			case LIST: {
				LinearLayoutManager layoutManager = new LinearLayoutManager(
						getActivity(), LinearLayoutManager.VERTICAL, false);
				mHotelsRecyclerView.setLayoutManager(layoutManager);
			}
			break;

			case GRID: {
				GridLayoutManager layoutManager = new GridLayoutManager(
						getActivity(), 2);
				mHotelsRecyclerView.setLayoutManager(layoutManager);
			}
			break;

			case STAGGERED: {
				StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
				mHotelsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
			}
			break;
		}
		mHotelsRecyclerView.setAdapter(mAdapter);
		mAdapter.setViewMode(mCurrentViewType);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		if(!mIsComingBack) {
			mBrowseHotelsPresenter.onStart();
		} else {
			mIsComingBack = false;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mBrowseHotelsPresenter.onResume();
		autoUnsubBus();
		subscribeBus();
	}

	private void subscribeBus() {
		mBusSubscription = mEventBus.toObserverable()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new Action1<Object>() {
							@Override
							public void call(Object o) {
								handlerBus(o);
							}
						}
				);
	}

	private void autoUnsubBus() {
		if (mBusSubscription != null && !mBusSubscription.isUnsubscribed()) {
			mBusSubscription.unsubscribe();
		}
	}

	private void handlerBus(Object o) {
		if (o instanceof BusEvents.HotelClickedEvent) {
			BusEvents.HotelClickedEvent event = (BusEvents.HotelClickedEvent) o;
			onHotelClicked(event.hotel);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mBrowseHotelsPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mBrowseHotelsPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBrowseHotelsPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mHotelsRecyclerView.setAdapter(null);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(GetHotelsApiResponse response) {
		mAdapter.addData(response);
	}

	@Override
	public void onFailure(String errorMsg) {

	}

	@OnClick(R.id.fabbutton)
	public void onToogleViewClicked() {
		switch(mCurrentViewType) {
			case LIST: {
				mCurrentViewType = ViewType.GRID;
			}
			break;

			case GRID: {
				mCurrentViewType = ViewType.STAGGERED;
			}
			break;

			case STAGGERED: {
				mCurrentViewType = ViewType.LIST;
			}
			break;
		}
		setUpRecyclerView();
		setUpFabBtn();
	}

	private void setUpFabBtn() {
		switch(mCurrentViewType) {
			case LIST: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_grid_on_white_36dp));
			}
			break;

			case GRID: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_dashboard_white_36dp));
			}
			break;

			case STAGGERED: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_view_list_white_36dp));
			}
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CURRENT_VIEW_TYPE, mCurrentViewType.getCode());
		outState.putParcelableArrayList(CURRENT_HOTEL_LIST, mAdapter.getHotelList());
	}

	private void onHotelClicked(Hotel hotel) {
		mBrowseHotelsPresenter.onHotelSelected(hotel);
		Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivityForResult(intent, REQUEST_CODE_DETAIL_HOTEL);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_DETAIL_HOTEL) {
			mIsComingBack = true;
		}
	}

	@Override
	public void onLoadStateChanged(boolean isLoading) {
		if(isLoading) {
			mLoadProgressView.setVisibility(View.VISIBLE);
		} else {
			mLoadProgressView.setVisibility(View.GONE);
		}
	}
}
