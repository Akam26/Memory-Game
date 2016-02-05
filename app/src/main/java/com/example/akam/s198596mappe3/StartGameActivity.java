package com.example.akam.s198596mappe3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class StartGameActivity extends AppCompatActivity{
    private Button startKnapp;
    private Button pauseButton;
    private boolean activeGame = false;
    private TextView timer;
    MenuItem startItem;
    MenuItem pauseItem;
    private long startTime = 0L;
    private long  endTime=0L;
    private long startPause = 0L;
    private long  endPause=0L;
    private long  pauseTime=0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int[] imageIDs;
    int turnone;
    int turntwo;
    int antallKlikk = 0;
    ArrayList<Object> correctIDs = new ArrayList<Object>();
    //ArrayList<Long> scores =new ArrayList<Long>();
    boolean turnstate = true;
    ImageButton klikk1;
    ImageButton klikk2;
    long score;
    long highestScore ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageValg.loadLang(this);
        setContentView(R.layout.activity_newgame);
        // = (Button)findViewById(R.id.startKnapp);

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        highestScore  = pref.getLong("HighestScore",922337203);

        /*SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = getPreferences(Activity.MODE_PRIVATE).edit();
        editor.putString("scoringer", scores.toString());*/
        //scores.add(highscore);
        //Log.d("SCoreTAG","Score:"+ scores);

        imageIDs = new int[]{
                R.drawable.dog,
                R.drawable.eagle,
                R.drawable.elefant,
                R.drawable.kanin,
                R.drawable.gurilla,
                R.drawable.lion,
                R.drawable.dog,
                R.drawable.eagle,
                R.drawable.elefant,
                R.drawable.kanin,
                R.drawable.gurilla,
                R.drawable.lion,

        };
        int[] buttonIDs = {
                R.id.btn_1,
                R.id.btn_2,
                R.id.btn_3,
                R.id.btn_4,
                R.id.btn_5,
                R.id.btn_6,
                R.id.btn_7,
                R.id.btn_8,
                R.id.btn_9,
                R.id.btn_10,
                R.id.btn_11,
                R.id.btn_12
        };

        Random r = new Random();
        ArrayList<Integer> printedImagesIDs = new ArrayList<>();

        ArrayList<ImageButton> buttons = new ArrayList<ImageButton>();

        //fyller buttons
        for(int i = 0; i < buttonIDs.length; i++){
            ImageButton temp = (ImageButton) findViewById(buttonIDs[i]);
            buttons.add(temp);
        }

        //populerer buttons med images
        for(int i = 0; i < buttons.size(); i++) {
            int random = r.nextInt(buttons.size());
            while(printedImagesIDs.contains(random))
                random = r.nextInt(buttons.size());

            buttons.get(i).setImageResource(imageIDs[random]);
            buttons.get(i).setTag(""+imageIDs[random]);
            printedImagesIDs.add(random);
        }

        final ArrayList<ImageButton> final_buttons = buttons;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                flipImages(final_buttons);
            }
        }, 2000);


        timer = (TextView) findViewById(R.id.timer);

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_startgame, menu);
        return true;
    }



    public void flipImages(ArrayList<ImageButton> buttons){
        for(int i = 0; i < buttons.size(); i++){
            Drawable temp = buttons.get(i).getDrawable();
            temp.setAlpha(0); //0-255, 0 = gjennomsiktig, 255 = fullt synlig
        }
    }

    public void test(final View view){ //kalles hver gang et bilde klikkes

        ImageButton btn = (ImageButton)findViewById(view.getId());
        if(antallKlikk == 2 || correctIDs.contains( btn ) || activeGame == false) {
            //skal returnere på det tredje klikket eller hvis knappen er korrekt fra før
            return;
        }
        Drawable draw = btn.getDrawable();
        draw.setAlpha(255);
        if(turnstate){
            antallKlikk++;
            turnone = btn.getId();
            klikk1 =btn;
            turnstate = false;
        }
        else {
            antallKlikk++;
            turntwo= btn.getId();
            klikk2 =btn;

        //alternativ som virker ikke
            Log.d("KilkkTag:","Value1: "+ klikk1.getTag());
            Log.d("KilkkTag:", "Value1: " + klikk2.getTag());
            //if((String)klikk1.getTag().equals((String)klikk2.getTag())){
            if(((String)klikk1.getTag()).equals((String)klikk2.getTag())){
                Log.d("KilkkTag:","Value: "+ klikk1.getTag());
                Log.d("KilkkTag:", "Value: " + klikk2.getTag());
            //if(klikk1.getDrawable().getConstantState().equals(klikk2.getDrawable().getConstantState())){//found pair
                correctIDs.add(klikk1);
                correctIDs.add(klikk2);
                antallKlikk = 0;
                turnstate = true;
            }
            else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Drawable draw1 = klikk1.getDrawable();
                        Drawable draw2 = klikk2.getDrawable();
                        draw1.setAlpha(0);
                        draw2.setAlpha(0);
                        antallKlikk = 0;
                    }
                }, 2000);
            }
            turnstate = true;
        }
        if(correctIDs.size()==imageIDs.length){
            endTime=SystemClock.uptimeMillis();
            endTime -= pauseTime;
            score =endTime-startTime;

            final Context context= this;
            //Log.d("ScoreTag:", "Scoring: " + scores);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.gratis)

                    .setMessage(getString(R.string.dinscore) + score +" ms")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            activeGame = false;
                            Intent intent = new Intent(context, Spill.class);

                           /* timeSwapBuff += timeInMilliseconds;
                            customHandler.removeCallbacks(updateTimerThread);*/
                            if(score<highestScore){
                                SharedPreferences pref = getPreferences(MODE_PRIVATE);
                                pref.edit().putLong("HighestScore", score).commit();
                            }
                            startActivity(intent);
                        }

                    })
                    .show();

        }
    }
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + mins + ":"
                            + String.format("%02d", secs) + ":"
                    /*+ String.format("%03d", milliseconds)*/);
            customHandler.postDelayed(this, 0);
        }

    };

    public boolean onOptionsItemSelected(MenuItem item) {
        Button startButton;
        final Context context = this;
        switch (item.getItemId()) {
            case R.id.close:
                activeGame = false;
                Intent intent = new Intent(context, Spill.class);
                finish();
                break;
            case R.id.startKnapp:
                /*if(startPause != 0){
                    endPause = SystemClock.uptimeMillis();
                    pauseTime += (endPause - startPause);
                }*/
                    activeGame = true;
                    startItem = item;
                    if(pauseItem != null)
                        pauseItem.setVisible(true);

                    startTime = SystemClock.uptimeMillis();
                   // score=startTime;
                    customHandler.postDelayed(updateTimerThread, 0);

                    //item.setVisible(false);
                    break;
            /*case R.id.pause:
                startPause = SystemClock.uptimeMillis();
                activeGame = false; //pauset spill
                pauseItem = item;
                startItem.setVisible(true);

                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

                item.setVisible(false);
                break;*/
            case R.id.score:
                //long highestScore =scores.get(0);
                //Log.d("ScoreTag:", "BB: " + scores);
               // long highestScore=Collections.sort(scores.size()-1);
                //long highestScore=scores.indexOf(Collections.min(scores));
                /*String lestScore = lesScore();
                if(Long.parseLong(lestScore)>score){
                    try {
                        lagreScore(score);
                    }
                    catch (Exception e){

                    }
                }*/
                //Collections.sort(scores);
                //long highestScore = Long.parseLong(lestScore);
               /*for(int i=0;i < scores.size();i++) {
                    if (scores.get(i) < highestScore && highestScore ==0) {
                        //Log.d("ScoringTag","CC"+scores.get(i));
                        highestScore = scores.get(i);

                        //scores.add(highestScore);

                    }
                }*/
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Score")
                                                    //highestScore
                        .setMessage(getString(R.string.høgesteScore) + highestScore + " ms")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        })
                        .show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void startGame(View v){
        Intent intent= new Intent(this,StartGameActivity.class);
        startActivity(intent);
    }

}