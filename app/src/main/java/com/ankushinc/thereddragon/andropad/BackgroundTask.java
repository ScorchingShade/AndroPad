package com.ankushinc.thereddragon.andropad;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Ankush on 8/12/2017.
 */

public class BackgroundTask extends AsyncTask<String,Note,String> {

    Context ctx;
    NoteAdapter noteAdapter;
    Activity activity;

    ListView listView;

   BackgroundTask(Context ctx) {
        this.ctx = ctx;
        activity=(Activity)ctx;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String method= params[0];

        Databasehelper databasehelper=new Databasehelper(ctx);
        if(method.equals("add_info")){
            String Title=params[1];
            String Note=params[2];
           SQLiteDatabase db =databasehelper.getWritableDatabase();

            databasehelper.insertData(Title,Note);

            return "Note saved";
        }

        else if(method.equals("get_info")){

            listView=(ListView) activity.findViewById(R.id.listView);
            SQLiteDatabase db=databasehelper.getReadableDatabase();
            Cursor cursor =databasehelper.getAllData(db);

            noteAdapter=new NoteAdapter(ctx,R.layout.list_base_layout);

            String id,title,note;

            while(cursor.moveToNext()){
                id=cursor.getString(cursor.getColumnIndex("ID"));
                title=cursor.getString(cursor.getColumnIndex("Title"));
                note=cursor.getString(cursor.getColumnIndex("Note"));

                Note note1=new Note(id,title,note);
                publishProgress(note1);

                return "get_info";
            }
        }


        return null;
    }


    @Override
    protected void onProgressUpdate(Note... values) {

        noteAdapter.add(values[0]);

    }


    @Override
    protected void onPostExecute(String result) {
        try{
        if (result.equals("get_info")) {
            listView.setAdapter(noteAdapter);

        } else {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }}
        catch (Exception e){}
    }
}
