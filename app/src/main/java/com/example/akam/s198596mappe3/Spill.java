package com.example.akam.s198596mappe3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;

public class Spill extends AppCompatActivity {

    final String LANGUAGE = "Language";
    final String NOR = "nor";
    final String ENG = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageValg.loadLang(this);
        setContentView(R.layout.activity_spill);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_spill, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        final Context context = this;
        switch (item.getItemId()) {
            case R.id.rules:
                Intent intent = new Intent(context, Rules.class);
                startActivity(intent);
                break;
             case R.id.norsk:
                    LanguageValg.changeLang(NOR, this);

                    intent = new Intent(context, Spill.class);
                    startActivity(intent);
                    LanguageValg.loadLang(this);
                    finish();
                    break;

                case R.id.eng:
                    LanguageValg.changeLang(ENG, this);
                    intent = new Intent(context, Spill.class);
                    startActivity(intent);
                    LanguageValg.loadLang(this);
                    finish();

                    break;
            }
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            //System.exit(0);*/
        return super.onOptionsItemSelected(item);
    }

    public void startGame(View v){
        Intent intent= new Intent(this,StartGameActivity.class);
        startActivity(intent);
    }
}
