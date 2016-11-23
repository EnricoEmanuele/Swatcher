package sweng.swatcher.activity;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


/*
    Lanciare questo test con la gallery vuota.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteMediaTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private static ConnectionSettingFragmentTest connSettingTest;

    @BeforeClass
    public static void setUp(){
        connSettingTest = new ConnectionSettingFragmentTest();
        Log.i("GALLERY_SETTING_TEST","SetUp done!");
    }

    @Before
    public void beforeTestSetUp(){
        connSettingTest.setUp();
        connSettingTest.connectionSettingFragmentTest();
    }

    @Test
    public void deleteMediaTest() {

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.play),
                        withParent(withId(R.id.relativeLayout)),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        try {
            Thread.sleep(TestUtilValues.GALLERY_TIME); //waiting play
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.snapshot),
                        withParent(withId(R.id.relativeLayout)),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Gallery"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        try {
            Thread.sleep(TestUtilValues.GALLERY_TIME); //waiting snackbar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.gallery_button),
                        withParent(withId(R.id.gallery_rel_lay)),
                        isDisplayed()));
        floatingActionButton4.check(matches(isDisplayed()));
        floatingActionButton4.perform(click());

        try {
            Thread.sleep(TestUtilValues.GALLERY_TIME); //waiting list
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.deleteButton),
                        withParent(allOf(withId(R.id.single_item_gallery),
                                childAtPosition(
                                        withId(R.id.gallery_list_view),
                                        1))),
                        isDisplayed()));
        floatingActionButton5.perform(click());


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
