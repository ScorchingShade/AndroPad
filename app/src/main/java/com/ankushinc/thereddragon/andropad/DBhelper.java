package com.ankushinc.thereddragon.andropad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ankush on 12/9/2017.
 */

public class DBhelper extends SQLiteOpenHelper {

    Context ctx;

    public static final String DATABASE_NAME ="Andropad.db";
    public static final String TABLE_NAME ="note";
    public static final String TITLE ="title";
    public static final String NOTE_DATA ="notedata";
    public static final String ID ="id";

    public DBhelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table note " +
                        "(id integer primary key, title text,notedata text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL( "DROP TABLE IF EXISTS note");
        onCreate(db);
    }


    public boolean insertNote (String title, String notedata) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("notedata", notedata);

        db.insert("note", null, contentValues);
        return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select title from "+TABLE_NAME, null );
        return res;
    }

    public String getNoteData(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select notedata from "+TABLE_NAME+" where title ='"+title+"'",null  );

        String data=null;
        if (res.moveToFirst()) {
            do {
                // get the data into array, or class variable
                data=res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        return data;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateNote (String title, String notedata) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("notedata", notedata);

        db.update("note", contentValues, "title = ? ", new String[] { title } );
        return true;
    }

    public Integer deleteNote (String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"title = ?",new String[]{title});

    }

    public ArrayList<String> getAllNotes() {
        ArrayList<String> array_list = new ArrayList<String>();
        array_list.add("Notes here");

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        if(db!=null) {
            Cursor res = db.rawQuery("select * from note", null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex(TITLE)));
                res.moveToNext();
            }
        }
        else {
            Toast toast=Toast.makeText(ctx,"Hello Javatpoint",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }
        return array_list;


    }


}
