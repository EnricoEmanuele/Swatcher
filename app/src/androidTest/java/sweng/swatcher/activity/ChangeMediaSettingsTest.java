package sweng.swatcher.activity;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import sweng.swatcher.R;
import sweng.swatcher.util.TestUtilValues;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChangeMediaSettingsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private static ConnectionSettingFragmentTest connSettingTest;
    private static String qualityImage, pictureType, maxMovieTime, snapOnDetect, threshold, snapInterval;
    private static String qualityImageTestIn, pictureTypeTestIn, maxMovieTimeTestIn, snapOnDetectTestIn, thresholdTestIn, snapIntervalTestIn;
    private ViewInteraction qualityEditText, picTypeSpinner, recMovieSwitch, maxMovieTimeEditText, snapOnDetectSpinner, threshholdEditText, snapIntervalEditText;

    @BeforeClass
    public static void setUp(){
        //set up oracle parameters
        setOracles();
        //set test inputs
        setTestInputs();
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
    public void changeParamsValuesTest(){

        try {
            Thread.sleep(TestUtilValues.SNACKBAR_TIME); //waiting for Motion restart
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //read values from Server
        /*ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.update_ms_button)));
        floatingActionButton2.perform(click());*/

        ViewInteraction floatingActionButton2 = onView(
                withId(R.id.update_ms_button));
        floatingActionButton2.perform(scrollTo(), click());

        //change values
        changeQualityImageTest(qualityImageTestIn);
        changePictureTypeTest(pictureTypeTestIn);
        changeRecMovieOnDetectTest(); //disable rec
        changeMaxMovieTimeTest(maxMovieTimeTestIn);
        changeSnapOnDetectTest(snapOnDetectTestIn);
        changeThresholdTest(thresholdTestIn);
        changeSnapIntervalTest(snapIntervalTestIn);

        //save new values on Server
        ViewInteraction buttonSave = onView(
                allOf(withId(R.id.save_ms_button)));
        buttonSave.perform(scrollTo(),click());

        try {
            Thread.sleep(TestUtilValues.MOTION_RESTART_TIME); //waiting for Motion restart
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        floatingActionButton2.perform(scrollTo(),click()); //read new value from server

        //assert
        qualityEditText.check(matches(withText(qualityImageTestIn)));
        picTypeSpinner.check(matches(withSpinnerText(pictureTypeTestIn)));
        recMovieSwitch.check(matches(not(isChecked())));
        maxMovieTimeEditText.check(matches(withText(maxMovieTimeTestIn)));
        snapOnDetectSpinner.check(matches(withSpinnerText(snapOnDetectTestIn)));
        threshholdEditText.check(matches(withText(thresholdTestIn)));
        snapIntervalEditText.check(matches(withText(snapIntervalTestIn)));

        //reset values
        changeQualityImageTest(qualityImage);
        changePictureTypeTest(pictureType);
        changeRecMovieOnDetectTest(); //enable rec
        changeMaxMovieTimeTest(maxMovieTime);
        changeSnapOnDetectTest(snapOnDetect);
        changeThresholdTest(threshold);
        changeSnapIntervalTest(snapInterval);

        buttonSave.perform(scrollTo(),click());

        /*
         * possible update: assert after reset
        try {
            Thread.sleep(10000); //waiting for Motion restart
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

    private void changeQualityImageTest(String value){
        //change quality image value
        qualityEditText = onView(
                allOf(withId(R.id.quality_image)));
        qualityEditText.perform(scrollTo(),click());
        qualityEditText.perform(scrollTo(),replaceText(value), closeSoftKeyboard());
    }

    private void changePictureTypeTest(String value){
        //change pivture type value
        picTypeSpinner = onView(
                allOf(withId(R.id.picture_type)));
        picTypeSpinner.perform(scrollTo(),click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText(value)));
        appCompatCheckedTextView3.perform(click());
    }

    private void changeRecMovieOnDetectTest(){
        //change switch value
        recMovieSwitch = onView(
                allOf(withId(R.id.movie_switch)));
        recMovieSwitch.perform(scrollTo(),click());
    }

    private void changeMaxMovieTimeTest(String value){
        //change max movie time value
        maxMovieTimeEditText = onView(
                allOf(withId(R.id.max_movie_time)));
        maxMovieTimeEditText.perform(scrollTo(),replaceText(value), closeSoftKeyboard());
    }

    private void changeSnapOnDetectTest(String value){
        //change snapshot on detection value
        snapOnDetectSpinner = onView(
                allOf(withId(R.id.snapshot_spinner)));
        snapOnDetectSpinner.perform(scrollTo(),click());

        ViewInteraction snapOnDetectTextView = onView(
                allOf(withId(android.R.id.text1), withText(value), isDisplayed()));
        snapOnDetectTextView.perform(click());
    }

    private void changeThresholdTest(String value){
        //change threshold value
        threshholdEditText = onView(
                allOf(withId(R.id.threshold)));
        threshholdEditText.perform(scrollTo(),replaceText(value), closeSoftKeyboard());
    }

    private void changeSnapIntervalTest(String value){
        //change snapshot interval value
        snapIntervalEditText = onView(
                allOf(withId(R.id.snapshot_interval)));
        snapIntervalEditText.perform(replaceText(value), closeSoftKeyboard());
    }

    private static void setOracles(){
        qualityImage = "90";
        pictureType = "jpeg";
        maxMovieTime = "30";
        snapOnDetect = "best";
        threshold = "1500";
        snapInterval = "0";
    }

    private static void setTestInputs(){
        qualityImageTestIn = "100";
        pictureTypeTestIn = "ppm";
        maxMovieTimeTestIn = "25";
        snapOnDetectTestIn = "first";
        thresholdTestIn = "1400";
        snapIntervalTestIn = "20";
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