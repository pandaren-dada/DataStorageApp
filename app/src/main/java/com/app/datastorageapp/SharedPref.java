package com.app.datastorageapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    static SharedPreferences SharedPref;

    public SharedPref(Context context){
        SharedPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
    }

    public void saveData(String userName, String password){

        SharedPreferences.Editor editor = SharedPref.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.commit();

    }

    public static String loadData(){

        String fileContent = "userName" + SharedPref.getString("userName", "No Name");
                fileContent += "password" + SharedPref.getString("password", "No Password");
                return fileContent;
    }
}
