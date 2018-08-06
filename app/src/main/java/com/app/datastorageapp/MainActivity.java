package com.app.datastorageapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBManager dbManager;

    SharedPref sharedPref;

    EditText userName, passWord;
    TextInputLayout usernameLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);

        sharedPref = new SharedPref(this);

        usernameLayout = (TextInputLayout)findViewById(R.id.userName_layout);
        passwordLayout = (TextInputLayout)findViewById(R.id.password_layout);
    }

    public void btn_signIn(View view) {

        userName = (EditText)findViewById(R.id.userName_text);
        passWord = (EditText)findViewById(R.id.password_text);

        //sharedPref.saveData(userName.getText().toString(),passWord.getText().toString());
        ContentValues values = new ContentValues();
        values.put(DBManager.colUserName, userName.getText().toString());
        values.put(DBManager.colPassword, passWord.getText().toString());
        long id = dbManager.insert(values);
        if (id > 0){
            Toast.makeText(getApplicationContext(),"Data is added and id = "+id , Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"failed to insert data", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_loadData(View view) {
        //String data = SharedPref.loadData();
        //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();

        //String[] projection = {"Username", "Password"};
        Cursor cursor = dbManager.query(null, null, null, DBManager.colUserName);
        if (cursor.moveToFirst()){
            String tableData = "";
            do{//tableData += cursor.getColumnIndex(DBManager.colUserName) to get index
                tableData += cursor.getString(cursor.getColumnIndex(DBManager.colUserName))+ "," +
                        cursor.getString(cursor.getColumnIndex(DBManager.colPassword))+ "::";
            }while (cursor.moveToNext());
            Toast.makeText(getApplicationContext(), tableData, Toast.LENGTH_LONG).show();
        }
    }
}
