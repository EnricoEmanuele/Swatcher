package sweng.swatcher.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
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

        Log.i("MEDIA_SETTING_TEST","SetUp done!");
    }

    @Before
    public void myBeforeMethod(){
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

    @Test
    public void changeQualityImageTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change quality image value
        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.quality_image), isDisplayed()));
        appCompatEditText10.perform(click());
        appCompatEditText10.perform(replaceText("90"), closeSoftKeyboard());

        Log.i("MEDIA_SETTING_TEST","change quality value done!");

        //click save button
        /*ViewInteraction buttonSave = onView(
                allOf(withId(R.id.save_ms_button), isDisplayed()));
        buttonSave.perform(click());*/
    }

    @Test
    public void changePictureTypeTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change pivture type value
        ViewInteraction picTypeSpinner = onView(
                allOf(withId(R.id.picture_type), isDisplayed()));
        picTypeSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("ppm"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());
    }

    @Test
    public void changeRecMovieOnDetectTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change switch value
        ViewInteraction switch_ = onView(
                allOf(withId(R.id.movie_switch), withText("Enabled"), isDisplayed()));
        switch_.perform(click());
    }

    @Test
    public void changeMaxMovieTimeTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change max movie time value
        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.max_movie_time), isDisplayed()));
        appCompatEditText12.perform(replaceText("25"), closeSoftKeyboard());
    }

    @Test
    public void changeSnapOnDetectTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change snapshot on detection value
        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.snapshot_spinner), isDisplayed()));
        appCompatSpinner2.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("center"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());
    }

    @Test
    public void changeThresholdTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change threshold value
        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.threshold), withText("1500"), isDisplayed()));
        appCompatEditText13.perform(replaceText("1400"), closeSoftKeyboard());
    }

    @Test
    public void changeSnapIntervalTest(){
        //click update button
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button), isDisplayed()));
        floatingActionButton2.perform(click());

        //change snapshot interval value
        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.snapshot_interval), withText("0"), isDisplayed()));
        appCompatEditText14.perform(replaceText("10"), closeSoftKeyboard());
    }

    private static void setOracles_remove(){

        Context context = InstrumentationRegistry.getTargetContext();
        String url = "http://82.49.5.100:4321/0/config/list";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("antonioTest",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MEDIA_SETTING_TEST", "Volley Res onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = authorization.getUsername()+":"+authorization.getPassword();
                Log.i("antonioTest",credentials);
                //String auth = authorization.getAuthType()+ " " + android.util.Base64.encodeToString(credentials.getBytes(), android.util.Base64.NO_WRAP);
                String auth = authorization.encodeAuthorization();
                headers.put("Authorization", auth);
                return headers;
            }
        };
        // Add the httpRequest to the RequestQueue.
        queue.add(stringRequest);

        qualityImage = "75";
        pictureType = "jpeg";
        recordOnDetect = true;
        maxMovieTime = "30";
        snapOnDetect = "best";
        threshold = "1500";
        snapInterval = "0";
    }

    public static void setOracles(){
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
