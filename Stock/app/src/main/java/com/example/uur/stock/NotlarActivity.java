package com.example.uur.stock;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.uur.stock.DBAdapter.NoteDBAdapter;
import com.example.uur.stock.ListAdapter.NoteAdapter;

import java.sql.SQLException;

/**
 * Created by Uğur on 13.5.2015.
 */
public class NotlarActivity extends ActionBarActivity {

    private static final String TAG = "Error";
    private static final int DELETE_ID = Menu.FIRST;
    private ListView list;
    private NoteDBAdapter mDbHelper;
    NoteAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notlar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator( R.drawable.back);
        mDbHelper = new NoteDBAdapter(this);
        try {
            mDbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fillData();

        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), NoteEdit.class);
                i.putExtra(NoteDBAdapter.KEY_ROWID, id);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.add:
                Log.i(TAG, "add");
                createNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        SearchManager searchManager =
                (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView)menu.findItem(R.id.menu_search)
                .getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    private void createNote() {
        Intent i = new Intent(this, NoteEdit.class);
        startActivity(i);
    }

    private void fillData() {
        Cursor notesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(notesCursor);


        String[] from = new String[] {
                NoteDBAdapter.KEY_TITLE,
                NoteDBAdapter.KEY_DATE };
        int[] to = new int[] { R.id.text1 ,R.id.date_row};

        // Now create an array adapter and set it to display using our row


        list = (ListView)findViewById(R.id.list);
        adapter = new NoteAdapter(this,notesCursor,1);
        list.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "Seçili Notu Silin");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteNote(info.id);
                fillData();
                Toast.makeText(getApplicationContext(),"Silindi",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}
