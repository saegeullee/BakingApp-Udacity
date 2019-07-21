package com.example.android.bakingapp;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RecipeStepFragment";

    private List<Step> mSteps;
    private int mCurrentStep;
    private int mStepsLength;

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private TextView mStepDescription, mStepShortDescription, mStepNavigationNotice;
    private Button mNextStepBtn, mPrevStepBtn;
    private ImageView mDefaultExoPlayerImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mSteps = new ArrayList<>();

        mSteps = getArguments().getParcelableArrayList(getString(R.string.recipe_steps));
        int position = getArguments().getInt(getString(R.string.step_number));
        mStepsLength = mSteps.size();
        mCurrentStep = position;

        Log.d(TAG, "onCreateView: steps : " + mSteps.toString());
        Log.d(TAG, "onCreateView: position : " + position);

        mPlayerView = rootView.findViewById(R.id.exo_player);
        mDefaultExoPlayerImage = rootView.findViewById(R.id.exoplayer_default_image);
        mStepDescription = rootView.findViewById(R.id.step_description);
        mStepShortDescription = rootView.findViewById(R.id.step_short_description);
        mStepNavigationNotice = rootView.findViewById(R.id.step_navigation_notice);
        mNextStepBtn = rootView.findViewById(R.id.next_step_btn);
        mPrevStepBtn = rootView.findViewById(R.id.prev_step_btn);
        mNextStepBtn.setOnClickListener(this);
        mPrevStepBtn.setOnClickListener(this);

        getExoPlayerReady(mCurrentStep);

        return rootView;
    }

    private void getExoPlayerReady(int position) {

        if(position == mStepsLength - 1) {
            mNextStepBtn.setEnabled(false);
        } else if(position == 0) {
            mPrevStepBtn.setEnabled(false);
        } else {
            mPrevStepBtn.setEnabled(true);
            mNextStepBtn.setEnabled(true);
        }

        String navigation_notice = (position+1) + "/" + mStepsLength;
        mStepNavigationNotice.setText(navigation_notice);
        mStepDescription.setText(mSteps.get(position).getDescription());
        mStepShortDescription.setText(mSteps.get(position).getShortDescription());

        if(mSteps.get(position).getVideoUrl().equals("")) {
            if(mSteps.get(position).getThumbnailUrl().equals("")) {

                Log.d(TAG, "getExoPlayerReady: both null null");

                mDefaultExoPlayerImage.setVisibility(View.VISIBLE);

                if(mExoPlayer != null) {
                    realisePlayer();
                }

//                mPlayerView.setDefaultArtwork(
//                        BitmapFactory.decodeResource(getResources(),
//                                R.drawable.ic_launcher_background)
//                );

            } else {
                mDefaultExoPlayerImage.setVisibility(View.GONE);
                initializeExoPlayer(Uri.parse(mSteps.get(position).getThumbnailUrl()));
            }
        } else {
            mDefaultExoPlayerImage.setVisibility(View.GONE);
            initializeExoPlayer(Uri.parse(mSteps.get(position).getVideoUrl()));
        }
    }

    private void initializeExoPlayer(Uri mediaUri) {

        if(mExoPlayer == null) {
            TrackSelector trackSelector =new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        } else {
            realisePlayer();
            initializeExoPlayer(mediaUri);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next_step_btn:

                mCurrentStep += 1;
                getExoPlayerReady(mCurrentStep);
                return;

            case R.id.prev_step_btn:
                mCurrentStep -= 1;
                getExoPlayerReady(mCurrentStep);
                return;

            default:

        }

    }

    public void realisePlayer() {
        Log.d(TAG, "realisePlayer: player realised");
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: in");
        super.onDestroy();
        realisePlayer();
    }


}
