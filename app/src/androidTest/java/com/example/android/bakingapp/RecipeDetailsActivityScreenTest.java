package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityScreenTest {

    private static final String STEP_DESCRIPTION = "Recipe Introduction";
    public static final String RECIPE_INGREDIENTS = "recipe ingredients";


    @Rule
    public ActivityTestRule<RecipeHomeActivity> mHomeActivityTestRule = new ActivityTestRule<>(RecipeHomeActivity.class);

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mDetailsActivityTestRule = new ActivityTestRule<>(RecipeDetailsActivity.class);

    @Test
    public void clickRecipeStepsRecyclerViewItem_OpensRecipeStepFragment() {

//        onView(withId(R.id.recipe_recyclerview))
//                .inRoot(RootMatchers.withDecorView(Matchers.is(mHomeActivityTestRule.getActivity().getWindow().getDecorView())))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//        onView(withId(R.id.textView)).check(matches(withText(RECIPE_INGREDIENTS)));


//        onView(withId(R.id.recipe_steps_recyclerview))
//                .inRoot(RootMatchers.withDecorView(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//        onView(withId(R.id.step_description)).check(matches(withText(STEP_DESCRIPTION)));
    }
}
