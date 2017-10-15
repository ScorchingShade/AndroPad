package com.ankushinc.thereddragon.andropad;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class NewNote extends AppCompatActivity {
    FloatingActionButton fab_plus,fab_new,fab_save,fab_trash;
    Animation FabOpen,FabClose,FabClockwise,FabAnticlockwise;
    EditText title,note;
    boolean isOpen=false;

    Context ctx;

    Databasehelper mydb;
    SQLiteDatabase db;
    BackgroundTask backgroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mydb=new Databasehelper(this);

        backgroundTask=new BackgroundTask(this);

        title=(EditText)findViewById(R.id.title_text);
        note=(EditText)findViewById(R.id.note_area);

         fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_new = (FloatingActionButton) findViewById(R.id.fab_new);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        fab_trash = (FloatingActionButton) findViewById(R.id.fab_trash);

        FabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabClockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabAnticlockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);


        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOpen){
                    fab_trash.startAnimation(FabClose);
                    fab_new.startAnimation(FabClose);
                    fab_save.startAnimation(FabClose);
                    fab_plus.startAnimation(FabAnticlockwise);


                    fab_new.setClickable(false);
                    fab_save.setClickable(false);
                    fab_trash.setClickable(false);
                    isOpen=false;
                }
                else{
                    fab_trash.startAnimation(FabOpen);
                    fab_new.startAnimation(FabOpen);
                    fab_save.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabClockwise);

                    fab_new.setVisibility(View.VISIBLE);
                    fab_trash.setVisibility(View.VISIBLE);
                    fab_save.setVisibility(View.VISIBLE);

                    fab_new.setClickable(true);
                    fab_save.setClickable(true);
                    fab_trash.setClickable(true);
                    isOpen=true;
                }


            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Untitled Note");

        addData();
        viewAll();
    }

    public void addData() {
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backgroundTask.execute("add_info",title.getText().toString(),note.getText().toString());
                NewNote.super.recreate();

            }
        });


    }


    public void viewAll(){
        fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=mydb.getAllData(db);
                if(res.getCount()==0){

                    alertbox("Error","Nothing here");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id: "+res.getString(0)+"\n");
                    buffer.append("Title: "+res.getString(1)+"\n");
                    buffer.append("Note: "+res.getString(2)+"\n");
                }

                alertbox("Notes",buffer.toString());

            }
        });
    }

    public void alertbox(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
