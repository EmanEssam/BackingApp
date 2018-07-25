package com.peacefulwarrior.eman.backingapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
    private int mStep;
    SimpleExoPlayer player;
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
        final Button backBtn = (Button) rootView.findViewById(R.id.back_btn);
        final Button nextBtn = (Button) rootView.findViewById(R.id.next_btn);
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
        bandwidthMeter = new DefaultBandwidthMeter();
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        final DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(currentStep.getVideoURL()),
                mediaDataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPosition--;
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
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepList.get(mCurrentPosition).getVideoURL().toString() + ""),
                        mediaDataSourceFactory, extractorsFactory, null, null);
                player.prepare(mediaSource);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stepList.get(mCurrentPosition).getVideoURL().toString() + ""),
                        mediaDataSourceFactory, extractorsFactory, null, null);
                player.prepare(mediaSource);


            }
        });
        return rootView;
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
