package com.example.pocketbots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Random;
/******************************************
 *   BATTLE SCREEN ACTIVITY
 *   The screen where the Java questions are asked, and the animations depend on
 *   whether the question was answered correctly.
 ******************************************/
public class BattleScreenActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button submitBTN;

    private int level;
    private int currentLevel;

    public int screenHeight;
    public int screenWidth;

    private int opponentHP;
    private int playerHP = 10;
    private int questionCounter;

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private Question currentQuestion;

    private boolean answered;

    private long backPressedTime;

    public SharedPreferences gameSettings;
    public SharedPreferences.Editor editGame;

    public ImageView robotImageView;
    public AnimationDrawable robotAnimation;

    public ImageView monsterImageView;
    public AnimationDrawable monsterAnimation;

    public ImageView playerHealth;
    public ImageView monsterHealth;

    private SoundPool soundPool;
    private int sound03;
    private int sound04;

    public View view;

    /******************************************
     *   ON CREATE
     ******************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_screen);

        // Find Image Views
        robotImageView = (ImageView) findViewById(R.id.robotImageView);
        monsterImageView = (ImageView) findViewById(R.id.monsterImageView);
        playerHealth = (ImageView) findViewById(R.id.playerHealth);
        monsterHealth = (ImageView) findViewById(R.id.monsterHealth);

        // Find Quiz Image Views
        textViewQuestion = findViewById(R.id.TextView_Question);
        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        submitBTN = findViewById(R.id.SubmitBTN);
        textColorDefaultRb = rb1.getTextColors();

        // Set the current level
        gameSettings = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        editGame = gameSettings.edit();
        level = gameSettings.getInt("level", 0);
        currentLevel = gameSettings.getInt("currentLevel", 1);

        // Cannot go past Level 7
        if (currentLevel == 8) {
            currentLevel--;
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
        sound03 = soundPool.load(this, R.raw.sound03, 1); // Correct Answer SFX
        sound04 = soundPool.load(this, R.raw.sound04, 1); // Incorrect Answer SFX
    }

    /******************************************
     *   START GAME
     ******************************************/
    protected void onStart() {
        super.onStart();

        // Make Monster Bigger if Level 7
        if (level >= 7) {
            ViewGroup.LayoutParams monsterParams = (ViewGroup.LayoutParams) monsterImageView.getLayoutParams();
            monsterParams.width *= 1.25;
            monsterParams.height *= 1.25;
        }

        // Get Height and Width of Screen and Move Robot and Monster as Necessary
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        Log.d("Dimensions", "Width: " + screenWidth + " Height: " + screenHeight);

        if (screenHeight < 900) {
            robotImageView.setY((float) (screenHeight * .054));
            monsterImageView.setY((float) (screenHeight * .054));
        } else {
            robotImageView.setY((float) (screenHeight * .006));
            monsterImageView.setY((float) (screenHeight * .006));
        }

        // Begin Idle Animations
        setRobotIdle();
        setMonsterIdle();

        // Set Background fro Health Bars
        opponentHP = 10;
        playerHealth.setBackgroundResource(R.drawable.health10);
        monsterHealth.setBackgroundResource(R.drawable.health10);

        // Begin Correct Quiz for the Current Level
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        if (level == 7) {
            questionList = dbHelper.getAllQuestions();
        } else {
            questionList = dbHelper.getQuestions(level);
        }
        Collections.shuffle(questionList);
        showNextQuestion();

        // Set On Click Listener for Submit Button
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(BattleScreenActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    /******************************************
     *   SHOW NEXT QUESTION
     *   Shows the next question
     ******************************************/
    private void showNextQuestion() {
        // rest the text color back to default
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);

        // Clear selection
        rbGroup.clearCheck();

        // ask another question
        if (opponentHP > 0 && playerHP > 0) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            Log.d("Question", "The current question is: " + currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            answered = false;
            submitBTN.setText("Submit");
        } else {
            finishQuiz();
        }
    }

    /******************************************
     *   CHECK ANSWER
     *   Checks the answer to the question,
     *   calls hit animations, sets health bars.
     ******************************************/
    private void checkAnswer() {
        answered = true;
        Random r = new Random();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNum = rbGroup.indexOfChild(rbSelected) + 1;

        // CORRECT ANSWER
        if (answerNum == currentQuestion.getAnswerNum()) {
            // Reduces hit by 1 if on last monster, and random otherwise
            if (level == 7) {
                opponentHP = opponentHP - 2;
            } else {
                //opponentHP = opponentHP - (r.nextInt(6 - 2) + 2);
                opponentHP = opponentHP - 5;
            }
            // Set monster health to 0 if he is dead
            if (opponentHP < 0) {
                opponentHP = 0;
            }

            // set health bar
            setHealth(monsterHealth, opponentHP);

            // Hit animations if monster is not dead
            if (opponentHP > 0) {
                hitMonster();
            }
            // INCORRECT ANSWER
        } else {
            // Player loses 5 hp and gets hit
            playerHP = playerHP - 5;
            hitRobot();

            // SFX Implementation
            switch (submitBTN.getId()) {
                case R.id.SubmitBTN:
                    soundPool.play(sound04, 1, 1, 0, 0, 1);
                    break;
            }

            // Player is Defeated, Toast and set health to 0
            if (playerHP <= 0) {
                Toast.makeText(this, "Your Pocketbot was DEFEATED :(", Toast.LENGTH_LONG).show();
                playerHP = 0;
            }

            Log.d("HP", "The Monsters HP: " + opponentHP + " The Players HP is " + playerHP);
            // set health bar
            setHealth(playerHealth, playerHP);
        }
        showSolution();
    }

    /******************************************
     *   SHOW SOLUTION
     *   Right answer is green, wrong are red
     ******************************************/
    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNum()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }

        // these action will need to be called after the fight animation
        if (opponentHP > 0) {
            submitBTN.setText("Next");
        } else {
            submitBTN.setText("Finish");
            hitMonster();
            if (level == currentLevel) {
                currentLevel++;
                editGame.putInt("currentLevel", currentLevel);
                editGame.commit();
                Log.d("Current Level", "Next level is " + currentLevel);
            }
        }
    }

    /******************************************
     *   FINISH QUIZ
     *   Go to Map View or Ending Activity
     ******************************************/
    private void finishQuiz() {
        if (currentLevel == 8) {
            Intent intent = new Intent(this, EndingActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MapViewActivity.class);
            startActivity(intent);
        }
    }

    /******************************************
     *   ON BACK PRESSED
     *   Show toast when back button is pressed on on the second press of back button will go to the MapViewActivity
     ******************************************/
    @Override
    public void onBackPressed() {
        // check the time between when you first press the back button and the second time to see if it is less then 2 seconds.
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to run away from the fight", Toast.LENGTH_SHORT).show();
        }

        // variable to save the time you press the back button
        backPressedTime = System.currentTimeMillis();
    }

    /******************************************
     *   SET ROBOT IDLE ANIMATION
     *   Set robot animation to idling
     ******************************************/
    public void setRobotIdle() {
        robotImageView.setBackgroundResource(R.drawable.robotidle);
        robotAnimation = (AnimationDrawable) robotImageView.getBackground();
        robotAnimation.start();
    }

    /******************************************
     *   SET MONSTER IDLE ANIMATION
     *   Set monster animation to idling
     ******************************************/
    public void setMonsterIdle() {
        switch (level) {
            case 1:
                monsterImageView.setBackgroundResource(R.drawable.redmonsteridle);
                break;
            case 2:
                monsterImageView.setBackgroundResource(R.drawable.skullidle);
                break;
            case 3:
                monsterImageView.setBackgroundResource(R.drawable.blueidle);
                break;
            case 4:
                monsterImageView.setBackgroundResource(R.drawable.greyidle);
                break;
            case 5:
                monsterImageView.setBackgroundResource(R.drawable.gridle);
                break;
            case 6:
                monsterImageView.setBackgroundResource(R.drawable.pinkidle);
                break;
            case 7:
                monsterImageView.setBackgroundResource(R.drawable.orangeidle);
                break;
        }
        monsterAnimation = (AnimationDrawable) monsterImageView.getBackground();
        monsterAnimation.start();
    }

    /******************************************
     *   HIT MONSTER
     *   Robot shoots the monster
     ******************************************/
    public void hitMonster() {
        // Monster Animation = Hit
        setMonsterHit();

        // Robot Animation = Shooting
        robotAnimation.stop();
        robotImageView.setBackgroundResource(R.drawable.robotshoot);
        robotAnimation = (AnimationDrawable) robotImageView.getBackground();
        robotAnimation.start();

        // SFX Implementation
        switch (submitBTN.getId()) {
            case R.id.SubmitBTN:
                soundPool.play(sound03, 1, 1, 0, 0, 1);
                break;
        }

        // Monster Animation = Idle after 1000ms
        if (opponentHP > 0) {
            monsterImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setMonsterIdle();
                }
            }, 1000);
        }
        // Robot Animation = Idle after 1000ms
        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                robotAnimation.stop();
                robotImageView.setBackgroundResource(R.drawable.robotidle);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotAnimation.start();
            }
        }, 1000);
    }

    /******************************************
     *   SET MONSTER HIT ANIMATION
     *   Monster animation is set to hit
     ******************************************/
    public void setMonsterHit() {
        monsterAnimation.stop();
        switch (level) {
            case 1:
                monsterImageView.setBackgroundResource(R.drawable.redhit);
                break;
            case 2:
                monsterImageView.setBackgroundResource(R.drawable.skullhit);
                break;
            case 3:
                monsterImageView.setBackgroundResource(R.drawable.bluehit);
                break;
            case 4:
                monsterImageView.setBackgroundResource(R.drawable.greyhit);
                break;
            case 5:
                monsterImageView.setBackgroundResource(R.drawable.grhit);
                break;
            case 6:
                monsterImageView.setBackgroundResource(R.drawable.pinkhit);
                break;
            case 7:
                monsterImageView.setBackgroundResource(R.drawable.orangehit);
                break;
        }
        monsterAnimation = (AnimationDrawable) monsterImageView.getBackground();
        monsterAnimation.start();
    }

    /******************************************
     *   HIT ROBOT
     *   Monster hits the robot
     ******************************************/
    public void hitRobot() {

        // Monster Attacks
        Animation animation = new TranslateAnimation(0, -650, 0, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        monsterImageView.startAnimation(animation);

        monsterImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Monster Moves Back to original position
                Animation animation2 = new TranslateAnimation(-650, 0, 0, 0);
                animation2.setDuration(1000);
                animation2.setFillAfter(true);
                monsterImageView.startAnimation(animation2);

                // Robot gets hit
                robotAnimation.stop();
                robotImageView.setBackgroundResource(R.drawable.robothit);
                robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                robotAnimation.start();
            }
        }, 200);

        // Set Robot back to Idle or Stunned if dead
        robotImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Player is still alive
                if (playerHP > 0) {
                    robotAnimation.stop();
                    robotImageView.setBackgroundResource(R.drawable.robotidle);
                    robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                    robotAnimation.start();
                } else { // Player is defeated
                    // Robot Stunned Animation
                    robotAnimation.stop();
                    robotImageView.setBackgroundResource(R.drawable.robotstunned);
                    robotAnimation = (AnimationDrawable) robotImageView.getBackground();
                    robotAnimation.start();

                    robotImageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // robot stays laying down
                            robotImageView.setBackgroundResource(R.drawable.robotstunned1);
                            robotAnimation.stop();
                        }
                    }, 2550);
                }
            }
        }, 800);
    }

    /******************************************
     *   SET HEALTH BAR ANIMATIONS
     *   Health bars are set based on HP values
     ******************************************/
    public void setHealth(ImageView healthBar, int health) {

        switch (health) {
            case 0:
                healthBar.setBackgroundResource(R.drawable.health0);
                break;
            case 1:
                healthBar.setBackgroundResource(R.drawable.health1);
                break;
            case 2:
                healthBar.setBackgroundResource(R.drawable.health2);
                break;
            case 3:
                healthBar.setBackgroundResource(R.drawable.health3);
                break;
            case 4:
                healthBar.setBackgroundResource(R.drawable.health4);
                break;
            case 5:
                healthBar.setBackgroundResource(R.drawable.health5);
                break;
            case 6:
                healthBar.setBackgroundResource(R.drawable.health6);
                break;
            case 7:
                healthBar.setBackgroundResource(R.drawable.health7);
                break;
            case 8:
                healthBar.setBackgroundResource(R.drawable.health8);
                break;
            case 9:
                healthBar.setBackgroundResource(R.drawable.health9);
                break;
            default:
                healthBar.setBackgroundResource(R.drawable.health10);
        }
    }

    /******************************************
     *   ON STOP
     ******************************************/
    @Override
    protected void onStop() {
        super.onStop();
        monsterAnimation.stop();
        robotAnimation.stop();
    }

}