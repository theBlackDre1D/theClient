package com.example.theblackdre1d.theclient.Java;

import android.media.MediaPlayer;
import android.widget.VideoView;

/**
 * Created by theBlackDre1D on 13. 10. 2017.
 */

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
