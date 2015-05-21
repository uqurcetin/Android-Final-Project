package com.example.uur.stock.ListAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.uur.stock.DBAdapter.NoteDBAdapter;
import com.example.uur.stock.R;

/**
 * Created by Uğur on 14.5.2015.
 */
public class NoteAdapter extends CursorAdapter{
    private LayoutInflater mInflater;

    public NoteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.notes_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        if(cursor.getPosition()%2==1) {
//            view.setBackgroundColor(context.getResources().getColor(R.color.dark_red));
//        }
//        else {
//            view.setBackgroundColor(context.getResources().getColor(R.color.dark_red));
//        }

        TextView mTitle = (TextView) view.findViewById(R.id.text1);
        TextView mDate = (TextView) view.findViewById(R.id.date_row);


        mTitle.setText(cursor.getString(cursor.getColumnIndex(NoteDBAdapter.KEY_TITLE)));
        mDate.setText(cursor.getString(cursor.getColumnIndex(NoteDBAdapter.KEY_DATE)));
    }
}

