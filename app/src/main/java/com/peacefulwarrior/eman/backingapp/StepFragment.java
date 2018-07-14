package com.peacefulwarrior.eman.backingapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static Step step2;


    public StepFragment(){}
//    public static StepFragment newInstance(int position, Step step) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_STEP, position);
//        StepFragment fragment = new StepFragment();
//        fragment.setArguments(args);
//        step2 = step;
//        return fragment;
//        // Required empty public constructor
//    }

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
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
        ImageView backBtn=(ImageView)rootView.findViewById(R.id.back_btn);
        ImageView nextBtn=(ImageView)rootView.findViewById(R.id.next_btn);
        bandwidthMeter = new DefaultBandwidthMeter();
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(getArguments().get("video")+""),
                mediaDataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);
        return rootView;
    }

}
