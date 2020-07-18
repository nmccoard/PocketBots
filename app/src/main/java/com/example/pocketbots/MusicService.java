package com.example.pocketbots;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/******************************************
 *   MusicService Class
 *   - Provides User with BGM (Background
 *   Music)
 *   - Allows programmer to use .mp3/.wav
 *   files
 ******************************************/
public class MusicService extends Service implements MediaPlayer.OnErrorListener {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;

    /******************************************
     *   MusicService
     ******************************************/
    public MusicService() {
    }

    /******************************************
     *   ServiceBinder
     ******************************************/
    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    /******************************************
     *   On Bind
     ******************************************/
    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    /******************************************
     *   On Create
     ******************************************/
    @Override
    public void onCreate() {
        super.onCreate();

        mPlayer = MediaPlayer.create(this, R.raw.sound02); // Add mp3 File Here
        mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(50, 50);
        }


        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra) {

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    /******************************************
     *   On Start Command
     ******************************************/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mPlayer != null) {
            mPlayer.start();
        }
        return START_NOT_STICKY;
    }

    /******************************************
     *   Pause Music
     ******************************************/
    public void pauseMusic() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                length = mPlayer.getCurrentPosition();
            }
        }
    }

    /******************************************
     *   Resume Music
     ******************************************/
    public void resumeMusic() {
        if (mPlayer != null) {
            if (!mPlayer.isPlaying()) {
                mPlayer.seekTo(length);
                mPlayer.start();
            }
        }
    }

    /******************************************
     *   Start Music
     ******************************************/
    public void startMusic() {
        mPlayer = MediaPlayer.create(this, R.raw.sound02); // Add mp3 File Here
        mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(50, 50);
            mPlayer.start();
        }

    }

    /******************************************
     *   Stop Music
     ******************************************/
    public void stopMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    /******************************************
     *   On Destroy
     ******************************************/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    /******************************************
     *   On Error
     ******************************************/
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "Music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
