package sweng.swatcher.activity;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import java.util.Map;
import sweng.swatcher.R;
import sweng.swatcher.model.Authorization;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MediaSettingFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private static ConnectionSettingFragmentTest connSettingTest;
    private static Authorization authorization;
    private static String qualityImage, pictureType, maxMovieTime, snapOnDetect, threshold, snapInterval;
    private static boolean recordOnDetect;

    @BeforeClass
    public static void setUp(){
        //set up oracle parameters
        authorization = new Authorization("u","p","Basic");
        setOracles();
        //run connection test
        connSettingTest = new ConnectionSettingFragmentTest();
    }

    @Before
    public void beforeTestSetUp(){
        //run connection setting test
        connSettingTest.setUp();
        connSettingTest.connectionSettingFragmentTest();

        //open Menu
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        //open Media Settings
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Media Settings"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());
    }

    @Test
    public void mediaSettingFragmentTest() {

        ViewInteraction msButton = onView(withId(R.id.save_ms_button));
        msButton.check(matches(not(isClickable())));

        ViewInteraction editText = onView(
                allOf(withId(R.id.quality_image),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        editText.check(matches(withText(""))); // empty String is default value

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("jpeg"),
                        childAtPosition(
                                allOf(withId(R.id.picture_type),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                4)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("jpeg"))); // "jpeg" = default value

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.movie_switch),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                7),
                        isDisplayed()));
        switch_.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.max_movie_time),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                9),
                        isDisplayed()));
        editText2.check(matches(withText(""))); // empty String is default value

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("on"),
                        childAtPosition(
                                allOf(withId(R.id.snapshot_spinner),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                11)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("on"))); // "on" = default value

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.threshold),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                13),
                        isDisplayed()));
        editText3.check(matches(withText(""))); // empty String is default value

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.snapshot_interval),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                15),
                        isDisplayed()));
        editText4.check(matches(withText(""))); // empty String is default value

        /*
         *  Read values from server
         */
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction editTextRead = onView(
                allOf(withId(R.id.quality_image),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        editTextRead.check(matches(withText(qualityImage)));

        ViewInteraction textViewRead = onView(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                allOf(withId(R.id.picture_type),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                4)),
                                0),
                        isDisplayed()));
        textViewRead.check(matches(withText(pictureType)));

        ViewInteraction switchRead = onView(
                allOf(withId(R.id.movie_switch),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                7),
                        isDisplayed()));
        if(recordOnDetect) switchRead.check(matches(isChecked()));
        else switchRead.check(matches(not(isChecked())));

        ViewInteraction editTextRead2 = onView(
                allOf(withId(R.id.max_movie_time),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                9),
                        isDisplayed()));
        editTextRead2.check(matches(withText(maxMovieTime)));

        ViewInteraction textViewRead2 = onView(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                allOf(withId(R.id.snapshot_spinner),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                11)),
                                0),
                        isDisplayed()));
        textViewRead2.check(matches(withText(snapOnDetect)));

        ViewInteraction editTextRead3 = onView(
                allOf(withId(R.id.threshold),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                13),
                        isDisplayed()));
        editTextRead3.check(matches(withText(threshold)));

        ViewInteraction editTextRead4 = onView(
                allOf(withId(R.id.snapshot_interval),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                15),
                        isDisplayed()));
        editTextRead4.check(matches(withText(snapInterval)));

        Log.i("MEDIA_SETTING_TEST","msft done!");
    }

    private static void setOracles(){
        qualityImage = "90";
        pictureType = "jpeg";
        recordOnDetect = true;
        maxMovieTime = "30";
        snapOnDetect = "best";
        threshold = "1500";
        snapInterval = "0";
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}
