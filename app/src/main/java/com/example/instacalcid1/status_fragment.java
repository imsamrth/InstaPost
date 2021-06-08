package com.example.instacalcid1;

import android.Manifest;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class status_fragment extends Fragment {

    FloatingActionButton add_remove_fab;
    SimpleExoPlayerView videoView ;
    SimpleExoPlayer exoPlayer ;
    TextView status_caption ;
    String userId ;
    boolean mode ;

    DatabaseReference databaseReference ;


    public status_fragment(String userId,boolean mode) {
        this.userId = userId ;
        this.mode = mode ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.status_fragment, container, false);

        Query query = FirebaseDatabase.getInstance().getReference("status").child(userId);
        add_remove_fab = view.findViewById(R.id.add_remove_fab);
        if(mode == false) add_remove_fab.setVisibility(View.INVISIBLE);
        videoView = view.findViewById(R.id.profile_activity_videoview);
        status_caption = view.findViewById(R.id.textView4);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                status_caption.setText(snapshot.child("caption").getValue(String.class));
                Toast.makeText(getContext(),snapshot.child("vurl").getValue(String.class),Toast.LENGTH_LONG).show();
                prepareexoplayer(snapshot.child("vurl").getValue(String.class));
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_remove_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(view.getContext(),addstatus_activity.class);
               view.getContext().startActivity(intent);
            }
        });
        return  view ;
    }
void  prepareexoplayer (String videourl){
        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer =(SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);

            Uri videoURI = Uri.parse(videourl);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

           videoView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        }catch (Exception ex){}
}



}