package com.example.akam.s198596mappe3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;

public class Rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageValg.loadLang(this);
        setContentView(R.layout.activity_rules);
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {

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

        }
        return super.onOptionsItemSelected(item);
    }*/
}
