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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sweng.swatcher.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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

    @Test
    public void mediaSettingFragmentTest() {

        //Connection Settings
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Connection Settings"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.IP1), isDisplayed()));
        appCompatEditText.perform(replaceText("79"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.IP2), isDisplayed()));
        appCompatEditText2.perform(replaceText("36"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.IP3), isDisplayed()));
        appCompatEditText3.perform(replaceText("161"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.IP4), isDisplayed()));
        appCompatEditText4.perform(replaceText("219"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.streaming_port), isDisplayed()));
        appCompatEditText5.perform(replaceText("5432"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.command_port), isDisplayed()));
        appCompatEditText6.perform(replaceText("4321"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.web_port), isDisplayed()));
        appCompatEditText7.perform(replaceText("80"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.username), isDisplayed()));
        appCompatEditText8.perform(replaceText("u"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.username), withText("u"), isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText10.perform(replaceText("p"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.password), withText("p"), isDisplayed()));
        appCompatEditText11.perform(pressImeActionButton());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.save_button), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        //Media Settings
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Media Settings"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction msButton = onView(withId(R.id.save_ms_button));
        msButton.check(matches(not(isClickable())));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.max_movie_time), isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.quality_image),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        editText.check(matches(withText("")));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("jpeg"),
                        childAtPosition(
                                allOf(withId(R.id.picture_type),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                4)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("jpeg")));

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
        editText2.check(matches(withText("")));

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("on"),
                        childAtPosition(
                                allOf(withId(R.id.snapshot_spinner),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                11)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("on")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.threshold),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                13),
                        isDisplayed()));
        editText3.check(matches(withText("")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.snapshot_interval),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                15),
                        isDisplayed()));
        editText4.check(matches(withText("")));

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
