package com.example.theblackdre1d.theclient.Java;

import android.media.MediaPlayer;
import android.widget.VideoView;

public class VideoViewOnPrepared {

    public VideoViewOnPrepared(VideoView video) {
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }
}
