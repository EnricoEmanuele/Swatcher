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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import sweng.swatcher.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsAnything.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GalleryFragmentTest {

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

        //open Menu
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        //open Gallery
        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Gallery"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());
    }

    @Test
    public void galleryFragmentTest() {

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Gallery"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction listView = onView(
                allOf(withId(R.id.gallery_list_view),
                        childAtPosition(
                                allOf(withId(R.id.gallery_rel_lay),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.gallery_button),
                        withParent(withId(R.id.gallery_rel_lay)),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.single_item_gallery),
                        childAtPosition(
                                allOf(withId(R.id.gallery_list_view),
                                        childAtPosition(
                                                withId(R.id.gallery_rel_lay),
                                                0)),
                                0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image), withContentDescription("Settings"),
                        childAtPosition(
                                allOf(withId(R.id.single_item_gallery),
                                        childAtPosition(
                                                withId(R.id.gallery_list_view),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        imageView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.close_button), withText(" CLOSE ")));
        appCompatButton2.check(matches(isDisplayed()));
        appCompatButton2.perform(click());

        /*ViewInteraction button = onView(
                allOf(withId(R.id.close_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.close_button), withText(" CLOSE "), isDisplayed()));
        appCompatButton3.perform(click());*/
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
