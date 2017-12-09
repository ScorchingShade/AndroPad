package com.ankushinc.thereddragon.andropad;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.*;

/**
 * Created by Ankush on 8/12/2017.
 */

public class NoteAdapter extends ArrayAdapter {

    List list=new ArrayList();
    private Context context;
    public NoteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(Note object) {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row=convertView;
        NoteHolder noteHolder;
        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.list_base_layout,parent,false);
            noteHolder=new NoteHolder();
            noteHolder.tx_title=(TextView)row.findViewById(R.id.list_base_title);

            row.setTag(noteHolder);
        }
        else{
            noteHolder=(NoteHolder)row.getTag();
        }

        Note note3=(Note)getItem(position);
        noteHolder.tx_title.setText(note3.getTitle().toString());


        return row;
    }

    static class NoteHolder{
        TextView tx_title;
    }

}
