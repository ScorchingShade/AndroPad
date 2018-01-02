package com.ankushinc.thereddragon.andropad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
    DBhelper db;
    Home home;

    Context ctx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db=new DBhelper(this);


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
        newNote();
        deleteNote();
    }

    public void addData() {
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=(EditText)findViewById(R.id.title_text);
                note=(EditText)findViewById(R.id.note_area);
                String Title=title.getText().toString();
                String Note=note.getText().toString();

                if(title.length()!=0){
                    AddData(Title,Note);
                }
                else {
                    Toast.makeText(NewNote.this, "Don't be lazy, atleast add a title!", Toast.LENGTH_SHORT).show();
                }


                NewNote.super.recreate();

            }
        });


    }
    public void AddData(String Title, String Note){
        boolean insertData=db.insertNote(Title,Note);

        if(insertData==true){
            Toast.makeText(NewNote.this, "We caught that and saved it!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(NewNote.this, "Oops! something went wrong, try again!", Toast.LENGTH_SHORT).show();
        }
    }


    public void newNote(){
        fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewNote.this,NewNote.class));
                Toast.makeText(NewNote.this,"New Note!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteNote(){
        fab_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=(EditText)findViewById(R.id.title_text);

                String Title=title.getText().toString();


                if(title.length()!=0){
                    delNote(Title);
                }
                else {
                    Toast.makeText(NewNote.this, "Seriously? How can we delete an empty note?", Toast.LENGTH_SHORT).show();
                }

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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(NewNote.this,Home.class));

        finish();
    }

    public void delNote(String Title){
        Integer insertData=db.deleteNote(Title);

        if(insertData>0){
            startActivity(new Intent(NewNote.this,NewNote.class));
            Toast.makeText(NewNote.this, "Poof! that's deleted!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(NewNote.this, "Oops! something went wrong, try again!", Toast.LENGTH_SHORT).show();
        }
    }

}
