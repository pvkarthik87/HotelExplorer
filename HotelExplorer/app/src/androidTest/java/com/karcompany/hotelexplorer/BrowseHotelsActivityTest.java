package com.karcompany.hotelexplorer;

/**
 * Created by pvkarthik on 2017-01-14.
 */

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karcompany.hotelexplorer.views.activities.BrowseHotelsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BrowseHotelsActivityTest {

	@Rule
	public ActivityTestRule<BrowseHotelsActivity> activityRule = new ActivityTestRule<>(BrowseHotelsActivity.class);

	@Test
	public void testUserProfileNavigation() {
		SystemClock.sleep(3000);
		onView(allOf(withId(R.id.hotel_list), isDisplayed())).perform(
				RecyclerViewActions.actionOnItemAtPosition(2, click()));
		SystemClock.sleep(3000);
		onView(withId(R.id.hotelPriceBar)).check(matches(isDisplayed()));
		onView(allOf(withId(R.id.hotel_image_list), isDisplayed())).perform(
				RecyclerViewActions.actionOnItemAtPosition(0, click()));
		SystemClock.sleep(3000);
		onView(allOf(withId(R.id.hotel_image_list), isDisplayed())).perform(
				RecyclerViewActions.actionOnItemAtPosition(0, click()));
		SystemClock.sleep(3000);
	}

}
