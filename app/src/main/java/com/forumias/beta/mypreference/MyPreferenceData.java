package com.forumias.beta.mypreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.forumias.beta.api.BaseUrl;

public class MyPreferenceData {

    private static MyPreferenceData myPreference;
    private SharedPreferences sharedPreferences;

    public static MyPreferenceData getInstance(Context context) {
        if (myPreference == null) {
            myPreference = new MyPreferenceData(context);
        }
        return myPreference;
    }

    public MyPreferenceData(Context context) {
        sharedPreferences = context.getSharedPreferences(BaseUrl.myPref,Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public void saveIntegerData(String key,int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putInt(key, value);
        prefsEditor.commit();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, null);
        }
        return null;
    }

    public Integer getIntegerData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }
    public boolean clearData(){
        sharedPreferences.edit().clear().commit();
        return true;
    }

    public void deleteSingleData(String key){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .remove(key);
        prefsEditor.commit();
    }
}
