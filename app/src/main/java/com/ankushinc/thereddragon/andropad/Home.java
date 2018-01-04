package com.ankushinc.thereddragon.andropad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    //declarations
    NewNote ob1 = new NewNote();
    DBhelper db;
    ArrayAdapter adapter;
    Toolbar toolbar;

    Context ctx;

    //for the bottom nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.quit:
                    System.exit(0);
                    return true;
                case R.id.new_note:
                    startActivity(new Intent(getApplicationContext(), NewNote.class));
                    finish();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Initializing DBhelper object db
        db = new DBhelper(this);

        //Initializing listview object and Arraylist Object, alo using cursor object to call cursor type function getdata
        ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = db.getData();

        //getcount==0 means that the cursor has nowhere to go and thus cant display anything
        if (cursor.getCount() == 0) {
            Toast.makeText(Home.this, "No notes to display", Toast.LENGTH_SHORT).show();
        } else {
            //if cursor has something move to next, move till it gets value
            while (cursor.moveToNext()) {
                //index of get string is 0 to get both 0 row and 0 column also
                arrayList.add(cursor.getString(0));
                adapter = new ArrayAdapter<String>(this, R.layout.list_base_layout, arrayList);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String title= adapterView.getItemAtPosition(i).toString();
                        Intent intent=new Intent(getApplicationContext(),ShowNotes.class);
                        intent.putExtra("title",title);
                        startActivity(intent);
                        finish();

                    }
                });


            }
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    /*public void alertbox(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    */

    //creating search feature in toolbar and inflating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.dark_action_menu,menu);
        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}



