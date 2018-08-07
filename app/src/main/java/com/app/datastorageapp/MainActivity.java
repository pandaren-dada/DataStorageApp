package com.app.datastorageapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBManager dbManager;

    SharedPref sharedPref;

    ArrayList<ListAdapter> listAdapters = new ArrayList<ListAdapter>();
    CustomAdapter mCustomAdapter;

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

        //add and show data

        listAdapters.clear();

        Cursor cursor = dbManager.query(null, null, null, DBManager.colUserName);
        if (cursor.moveToFirst()){
            String tableData = "";
            do{//tableData += cursor.getColumnIndex(DBManager.colUserName) to get index
                /*tableData += cursor.getString(cursor.getColumnIndex(DBManager.colUserName))+ "," +
                        cursor.getString(cursor.getColumnIndex(DBManager.colPassword))+ "::";
            */
                listAdapters.add(new ListAdapter(cursor.getString(cursor.getColumnIndex(DBManager.colId))
                        , cursor.getString(cursor.getColumnIndex(DBManager.colUserName)),
                        cursor.getString(cursor.getColumnIndex(DBManager.colPassword))));
            }while (cursor.moveToNext());
            Toast.makeText(getApplicationContext(), tableData, Toast.LENGTH_LONG).show();
        }

        mCustomAdapter=new CustomAdapter(listAdapters);
        ListView listData = (ListView)findViewById(R.id.list_item);
        listData.setAdapter(mCustomAdapter);//intisal with data
    }

    public class CustomAdapter extends BaseAdapter{

        public ArrayList<ListAdapter> mListAdapters;

        public CustomAdapter(ArrayList<ListAdapter> listAdapters){
            this.mListAdapters = listAdapters;
        }

        @Override
        public int getCount() {
            return mListAdapters.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.list_adapter, null);

            final ListAdapter listAdapter = mListAdapters.get(position);

            TextView textUserId = (TextView)findViewById(R.id.user_id);
            textUserId.setText(listAdapter.userName);

            TextView textUserName = (TextView)findViewById(R.id.user_name);
            textUserId.setText(listAdapter.userName);

            TextView textUserPassword = (TextView)findViewById(R.id.user_password);
            textUserId.setText(listAdapter.userName);

            return myView;
        }
    }
}
