package com.ankushinc.thereddragon.andropad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ankush on 8/6/2017.
 */

public class Databasehelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME="andropad.db";
    private static final String TABLE_NAME="andropad_table";
    public static final int DB_VERSION=1;


    private static final String COL_1="ID";
    private static final String COL_2="Title";
    private static final String COL_3="Note";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        Log.d("Database Operations","Database created...");


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Note TEXT );");
        Log.d("Database Operations","Table created...");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData( String title,String note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,note);
      long result=  db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(SQLiteDatabase db){
         db=this.getWritableDatabase();
        String[] projections={"ID, Title, Note "};

        Cursor cursor=db.query(TABLE_NAME,projections,null,null,null,null,null);

        //String Query="select * from "+TABLE_NAME;
        //Cursor result=db.rawQuery(Query,null);
        return cursor;
    }


}
