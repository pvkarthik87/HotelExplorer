package com.karcompany.hotelexplorer;

import com.karcompany.hotelexplorer.models.GetHotelsApiResponse;
import com.karcompany.hotelexplorer.models.Hotel;
import com.karcompany.hotelexplorer.networking.ApiRepo;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenter;
import com.karcompany.hotelexplorer.presenters.BrowseHotelsPresenterImpl;
import com.karcompany.hotelexplorer.views.BrowseHotelsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class BrowseHotelsPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private BrowseHotelsView view;

	private BrowseHotelsPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new BrowseHotelsPresenterImpl(model);
		presenter.setView(view);
		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				GetHotelsApiResponse getHotelsApiResponse = new GetHotelsApiResponse();
				List<Hotel> hotels = new ArrayList<>();
				Hotel hotel = new Hotel();
				hotels.add(hotel);
				getHotelsApiResponse.setHotel(hotels);
				((ApiRepo.GetHotelsApiCallback) presenter).onSuccess(getHotelsApiResponse);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getHotels((ApiRepo.GetHotelsApiCallback) presenter);
	}

	/**
	 Verify if model.getUserList was called once.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchHotels() {
		presenter.onStart();
		// verify can be called only on mock objects
		verify(model, times(1)).getHotels((ApiRepo.GetHotelsApiCallback) presenter);
		verify(view, times(1)).onDataReceived(any(GetHotelsApiResponse.class));
	}

}
