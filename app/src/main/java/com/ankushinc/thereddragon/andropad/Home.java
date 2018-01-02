package com.ankushinc.thereddragon.andropad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    NewNote ob1=new NewNote();
    String[] testArray={"as","asdas","sdsdsad"};
    DBhelper db;

    Context ctx;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.quit:
                    System.exit(0);
                    return true;
                case R.id.new_note:
                    startActivity(new Intent(getApplicationContext(),NewNote.class));
                    return true;
            }
            return false;
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        db=new DBhelper(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor=db.getData();

        if(cursor.getCount()==0){
            Toast.makeText(Home.this,"No notes to display",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                arrayList.add(cursor.getString(0));
                ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_base_layout, arrayList);
                listView.setAdapter(adapter);
            }
        }



        /*if(arrayList.isEmpty()) {
            Toast toast=Toast.makeText(getApplicationContext(),"Notes not found",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }
        else{
            ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_base_layout, arrayList);


            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }

        */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }



    public void alertbox(String title, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



}



