package com.example.akam.s198596mappe3;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class LanguageValg {

    public final static String LANGUAGE = "language";
    public final static String DEFAULT_LANGUAGE = "en";

    public static void changeLang(String lang, Context con){
        Resources res = con.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale=new Locale(lang);
        res.updateConfiguration(conf,dm);
        saveLang(lang,con);
    }

    private static void saveLang(String lang, Context con){
        SharedPreferences sprfs = con.getSharedPreferences(LANGUAGE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor =sprfs.edit();
        editor.putString(LANGUAGE, lang);
        editor.commit();
    }

    public static void loadLang(Context con){
        SharedPreferences sprfs = con.getSharedPreferences(LANGUAGE, Activity.MODE_PRIVATE);
        String lang= sprfs.getString(LANGUAGE, DEFAULT_LANGUAGE);
        changeLang(lang, con);

    }
}
