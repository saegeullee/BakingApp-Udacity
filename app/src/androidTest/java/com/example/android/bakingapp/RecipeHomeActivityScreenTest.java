package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeHomeActivityScreenTest {

    private static final String STEP_DESCRIPTION = "Recipe Introduction";
    public static final String RECIPE_INGREDIENTS = "recipe ingredients";

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */

    @Rule
    public ActivityTestRule<RecipeHomeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeHomeActivity.class);

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mDetailsActivityTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class);


    @Test
    public void clickRecyclerViewItem_OpensRecipeDetailsActivity() {

        onView(withId(R.id.recipe_recyclerview))
                .inRoot(RootMatchers.withDecorView(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.textView)).check(matches(withText(RECIPE_INGREDIENTS)));

    }

}
