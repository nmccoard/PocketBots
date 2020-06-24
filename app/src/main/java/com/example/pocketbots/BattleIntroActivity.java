package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BattleIntroActivity extends AppCompatActivity {

    Button exitButton;
    Button saveButton;
    Button battleButton;

    ImageView bgImageView;
    ImageView boyImageView;
    ImageView monsterImageView;

    AnimationDrawable boyAnimation;
    AnimationDrawable redMonsterIdleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_intro);

        bgImageView = (ImageView) findViewById(R.id.bgImageView);
        exitButton = (Button) findViewById(R.id.exitButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        battleButton = (Button) findViewById(R.id.battleButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        boyImageView = (ImageView) findViewById(R.id.boyImageView);
        boyImageView.setBackgroundResource(R.drawable.boyrun);
        boyAnimation = (AnimationDrawable) boyImageView.getBackground();
        boyImageView.setX(-350);

        monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        monsterImageView.setBackgroundResource(R.drawable.redmonsteridle);
        redMonsterIdleAnimation = (AnimationDrawable) monsterImageView.getBackground();
        //monsterImageView.setX(125%);

        boyAnimation.start();
        redMonsterIdleAnimation.start();
        boyImageView.animate().translationXBy(850).setDuration(3000);

        boyImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boyImageView.setBackgroundResource(R.drawable.boyidle);
                boyAnimation = (AnimationDrawable) boyImageView.getBackground();
                boyAnimation.start();
            }
        }, 3000);
    }

    public void exitGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void battle(View view) {
        Intent intent = new Intent(this, BattleScreenActivity.class);
        startActivity(intent);
    }
}