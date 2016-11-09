package sweng.swatcher.activity;

import android.support.annotation.StringRes;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SecuritySettingFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private static ConnectionSettingFragmentTest connSettingTest;
    private static String newUsername, newPassword;

    @BeforeClass
    public static void setUp(){
        connSettingTest = new ConnectionSettingFragmentTest();
        setNewCredentials();
    }

    @Before
    public void beforeTestSetUp(){
        connSettingTest.setUp();
        connSettingTest.connectionSettingFragmentTest();
    }

    @Test
    public void securitySettingFragmentTest() {

        ViewInteraction openMenu = onView(
                allOf(withContentDescription("Open Menu"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        openMenu.perform(click());

        ViewInteraction openSecuritySettings = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Security Settings"), isDisplayed()));
        openSecuritySettings.perform(click());

        //change credentials
        changeUsername(newUsername);
        changePassword(newPassword);

        try{
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        //click change button and wait for result
        ViewInteraction changeButton = onView(
                allOf(withId(R.id.change_button), isDisplayed()));
        changeButton.perform(click());
        checkSnackBarDisplayedByMessage(R.string.new_credentials_test_message); //snackbar assertion

        /*//reset credentials
        openMenu.perform(click());
        openSecuritySettings.perform(click());
        changeUsername(oldUsername);
        changePassword(oldPassword);

        try{
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        changeButton.perform(click());
        checkSnackBarDisplayedByMessage(R.string.old_credentials_test_message); //snackbar assertion
        */

    }

    private void checkSnackBarDisplayedByMessage(@StringRes int message) {
        onView(withText(message))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )));
    }

    private void changeUsername(String value){
        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.new_username), isDisplayed()));
        appCompatEditText11.perform(replaceText(value), closeSoftKeyboard());
    }

    private void changePassword(String value){
        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.new_password), isDisplayed()));
        appCompatEditText12.perform(replaceText(value), closeSoftKeyboard());
    }

    private static void setNewCredentials(){ //select current credentials for test
        newUsername = "u";
        newPassword = "p";
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
