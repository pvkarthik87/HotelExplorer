package com.karcompany.hotelexplorer.networking;

import com.karcompany.hotelexplorer.logging.DefaultLogger;
import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	public interface GetHotelsApiCallback {
		void onSuccess(GetHotelsApiResponse response);

		void onError(NetworkError networkError);
	}

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

	public Subscription getHotels(final GetHotelsApiCallback callback) {
		return mRestService.getHotels()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends GetHotelsApiResponse>>() {
					@Override
					public Observable<? extends GetHotelsApiResponse> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<GetHotelsApiResponse>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						NetworkError ne = new NetworkError(e);
						callback.onError(ne);
						DefaultLogger.d(TAG, "onError "+ne.getAppErrorMessage());
					}

					@Override
					public void onNext(GetHotelsApiResponse userList) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(userList);
					}
				});
	}

}
