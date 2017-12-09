package com.ankushinc.thereddragon.andropad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

public class Home extends AppCompatActivity {

    NewNote ob1=new NewNote();
    NoteAdapter noteAdapter;
    Context ctx;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.share:

                    return true;
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

        BackgroundTask backgroundTask=new BackgroundTask(this);

        backgroundTask.execute("get_info");


        ListView listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(noteAdapter);



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



