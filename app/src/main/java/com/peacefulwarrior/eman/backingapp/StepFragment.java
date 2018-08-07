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
    private int mStep;
    private SimpleExoPlayerView simpleExoPlayerView;

    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private List<Step> stepList;
    private Step currentStep;
    private int mCurrentPosition = 0;


    public StepFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mStep = getArguments().getInt(ARG_STEP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        stepList = getArguments().getParcelableArrayList("step");
        currentStep = stepList.get(getArguments().getInt("position"));
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            handleExoPlayer();
        } else {
            final ImageButton backBtn = (ImageButton) rootView.findViewById(R.id.back_btn);
            final ImageButton nextBtn = (ImageButton) rootView.findViewById(R.id.next_btn);
            final TextView instructionTv = (TextView) rootView.findViewById(R.id.instruction_tv);
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

            handleExoPlayer();
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
                    final DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepList.get(mCurrentPosition).getVideoURL()),
                            mediaDataSourceFactory, extractorsFactory, null, null);
                    player.prepare(mediaSource);
                }
            });
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    instructionTv.setText(stepList.get(mCurrentPosition).getDescription());

                    mCurrentPosition++;
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

                    final DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepList.get(mCurrentPosition).getVideoURL()),
                            mediaDataSourceFactory, extractorsFactory, null, null);
                    player.prepare(mediaSource);
                }
            });
        }
        return rootView;
    }

    private void handleExoPlayer() {
        bandwidthMeter = new DefaultBandwidthMeter();
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);

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
