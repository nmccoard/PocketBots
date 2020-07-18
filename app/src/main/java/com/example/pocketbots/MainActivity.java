
package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences gameSettings;
    Button loadGame;

    HomeWatcher mHomeWatcher;

    private SoundPool soundPool;
    private int sound00, sound01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        loadGame = findViewById(R.id.loadGameBtn);

        if(gameSettings.getInt("level", 0) == 0) {
            loadGame.setVisibility(View.GONE);
        }

        // MusicService Class Implementation
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        // HomeWatcher Class Implementation
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        loadGame = findViewById(R.id.loadGameBtn);

        if(gameSettings.getInt("level", 0) == 0) {
            loadGame.setVisibility(View.GONE);
        }

        // SoundPool Implementation (This is for SFX/Hitsounds, etc.)
        // Due to the old method and the new method we need to determine
        // if it is possible to use either or so an error would not produce
        // as a result of its implementation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        // Here we load the mp3 files after the tools are set.
        sound00 = soundPool.load(this, R.raw.sound00, 1);
        sound01 = soundPool.load(this, R.raw.sound01, 1);

    }

    // Implementing MusicService Class
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    // Continue the Music upon returning to the window.
    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    // Pause the Music upon User's Input.
    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

    }

    // Terminate the Music, Save System Resources
    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }

    public void newGame(View view) {
        // loadGame.setVisibility(View.GONE);
        // add option to ask for name
        // add another button to call another function to start the map activity

        // SFX Implementation
        switch (view.getId()) {
            case R.id.newGameBtn:
                soundPool.play(sound00, 1, 1, 0, 0, 1);
                break;
        }

        SharedPreferences.Editor editGame = gameSettings.edit();
        editGame.putInt("level", 1);
        editGame.putInt("currentLevel", 1);
        editGame.commit();
      
        Intent intent = new Intent(MainActivity.this, MapViewActivity.class);
        startActivity(intent);
    }

    public void loadGame(View view) {
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);

        switch (view.getId()) {
            case R.id.loadGameBtn:
                soundPool.play(sound01, 1, 1, 0, 0, 1);
                break;
        }
    }
}
