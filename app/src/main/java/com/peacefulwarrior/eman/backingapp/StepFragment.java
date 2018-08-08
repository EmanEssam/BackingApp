package com.peacefulwarrior.eman.backingapp;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.peacefulwarrior.eman.backingapp.model.Step;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    public static final String ARG_STEP = "ARG_STEP";
    SimpleExoPlayer player;
    TextView emptyView;
    private int mStep;
    private SimpleExoPlayerView simpleExoPlayerView;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private List<Step> stepList;
    private Step currentStep;
    private int mCurrentPosition = 0;
    private boolean mTwoPane = false;


    public StepFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        final ImageButton backBtn = (ImageButton) rootView.findViewById(R.id.back_btn);
        final ImageButton nextBtn = (ImageButton) rootView.findViewById(R.id.next_btn);
        final TextView instructionTv = (TextView) rootView.findViewById(R.id.instruction_tv);
        emptyView = rootView.findViewById(R.id.videoEmptyView);
        stepList = getArguments().getParcelableArrayList("step");
        mTwoPane = getArguments().getBoolean("tablet");
        if (getArguments().getBoolean("tablet")) {
            if (backBtn != null & nextBtn != null) {
                backBtn.setVisibility(View.GONE);
                nextBtn.setVisibility(View.GONE);
            }
        }
        currentStep = stepList.get(getArguments().getInt("position"));
        mCurrentPosition = getArguments().getInt("position");
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
        if (currentStep.getVideoURL().isEmpty()) {
            showEmptyView();
        } else {
            hideEmptyView();
            handleExoPlayer(currentStep);
        }

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (currentStep.getVideoURL().isEmpty()) {
                showEmptyView();
            } else {
                hideEmptyView();
                handleExoPlayer(currentStep);
            }
        } else {
            instructionTv.setText(currentStep.getDescription());
            if (mCurrentPosition == 0) {
//            backBtn.setVisibility(View.GONE);
                backBtn.setEnabled(false);
            } else {
                backBtn.setEnabled(true);
//            backBtn.setVisibility(View.VISIBLE);
            }
            if (mCurrentPosition == stepList.size() - 1) {
//            nextBtn.setVisibility(View.GONE);
//            nextBtn.setEnabled(false);
                nextBtn.setEnabled(false);
            } else {
//            nextBtn.setVisibility(View.VISIBLE);
                nextBtn.setEnabled(true);
            }
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPosition--;
                    instructionTv.setText(stepList.get(mCurrentPosition).getDescription());
                    if (mCurrentPosition == 0) {
//                    backBtn.setVisibility(View.GONE);
                        backBtn.setEnabled(false);
                    } else {
//                    backBtn.setVisibility(View.VISIBLE);
                        backBtn.setEnabled(true);
                    }
                    if (mCurrentPosition == stepList.size() - 1) {
//                    nextBtn.setVisibility(View.GONE);
                        nextBtn.setEnabled(false);
                    } else {
//                    nextBtn.setVisibility(View.VISIBLE);
                        nextBtn.setEnabled(true);
                    }
                    if (!stepList.get(mCurrentPosition).getVideoURL().isEmpty()) {
                        handleExoPlayer(stepList.get(mCurrentPosition));
                        emptyView.setVisibility(View.GONE);
                        simpleExoPlayerView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        simpleExoPlayerView.setVisibility(View.GONE);
                    }

                }
            });
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentPosition++;
                    instructionTv.setText(stepList.get(mCurrentPosition).getDescription());
                    if (mCurrentPosition == 0) {
//                    backBtn.setVisibility(View.GONE);
                        backBtn.setEnabled(false);

                    } else {
//                    backBtn.setVisibility(View.VISIBLE);
                        backBtn.setEnabled(true);
                    }
                    if (mCurrentPosition == stepList.size() - 1) {
//                    nextBtn.setVisibility(View.GONE);
                        nextBtn.setEnabled(false);

                    } else {
//                    nextBtn.setVisibility(View.VISIBLE);
                        nextBtn.setEnabled(true);
                    }
                    if (!stepList.get(mCurrentPosition).getVideoURL().isEmpty()) {
                        handleExoPlayer(stepList.get(mCurrentPosition));
                        emptyView.setVisibility(View.GONE);
                        simpleExoPlayerView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        simpleExoPlayerView.setVisibility(View.GONE);
                    }

                }
            });
        }
        return rootView;
    }

    private void showEmptyView() {
        simpleExoPlayerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyView() {
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

    }

    private void handleExoPlayer(Step step) {
        bandwidthMeter = new DefaultBandwidthMeter();
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                mediaDataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
//            player.removeListener(componentListener);
            player.release();
            player = null;
        }
    }
}
