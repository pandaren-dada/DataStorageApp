package com.app.datastorageapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import java.sql.SQLIntegrityConstraintViolationException;

public class DBManager {

    private SQLiteDatabase mSQLiteDatabase;

    static final String databaseName = "User";
    static final String tableName = "Member";
    static final String colUserName = "Username";
    static final String colPassword = "Password";
    static final String colId = "Id";
    static final int dbVersion = 1;

    static final String createTable = " CREATE TABLE IF NOT EXISTS "+ tableName +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ colUserName +
             " TEXT,"+ colPassword + " TEXT);";

    static final class DatabaseHelper extends SQLiteOpenHelper{

        Context mContext;
        public DatabaseHelper(Context context) {
            super(context, databaseName, null, dbVersion);
            this.mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createTable);
            Toast.makeText(mContext, "Table is Created", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(" Drop table IF EXISTS "+ tableName);
            onCreate(db);
        }
    }

    public DBManager(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        mSQLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public long insert(ContentValues values){
        long id = mSQLiteDatabase.insert(tableName, "", values);
        return id;
    }

    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(tableName);
        Cursor cursor = sqLiteQueryBuilder.query(mSQLiteDatabase, projection, selection, selectionArgs,null, null, sortOrder);
        return cursor;
    }
}
