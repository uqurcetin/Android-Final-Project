package com.example.uur.stock;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uur.stock.ListAdapter.TeslimEdilenlerAdapter;
import com.example.uur.stock.Model.TeslimEdilenlerModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uğur on 13.5.2015.
 */
public class TeslimEdilenlerActivitiy extends ActionBarActivity {

    private static final String TAG = "Error";
    private List<TeslimEdilenlerModel> modelTeslimEdilenler = new ArrayList<TeslimEdilenlerModel>();
    private TeslimEdilenlerAdapter adapter;
    private ListView lvTeslimEdilenler;
    private EditText edtSearchCustomer;
    
    private final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.24:1433;DatabaseName=DB_FinalProject";
    private final String DB_USER ="Testing";
    private final String DB_PASSWORD = "sa123";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_teslimedilenler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        lvTeslimEdilenler = (ListView)findViewById(R.id.lvTeslimEdilenler);
        adapter = new TeslimEdilenlerAdapter(this, modelTeslimEdilenler);
        lvTeslimEdilenler.setAdapter(adapter);

        try {
            fillListViewFromDB();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        }
        adapter.notifyDataSetChanged();

        edtSearchCustomer = (EditText)findViewById(R.id.edtSearchCustomer);
        edtSearchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Connection getDBConnection(){
        Connection conn = null;
        try{
            Class.forName(DB_DRIVER).newInstance();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return conn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    private void fillListViewFromDB() throws  SQLException{
        Connection conn = null;
        Statement statement =null;
        ResultSet resultSet = null;
        String caseOfWhere = "Teslim Edildi";
        String query = "SELECT S.SiparisID as siparisid, M.MusteriAdi as musteriadi, M.MusteriSoyadi as musterisoyadi," +
                " U.UrunAdi as urunadi FROM Sevkiyatlar S "+
                "INNER JOIN Musteriler M ON M.MusteriID=S.MusteriID " +
                "INNER JOIN Urunler U ON U.UrunID=S.urunID WHERE S.SevkiyatDurumu='"+caseOfWhere+"'";
        try{
            conn = getDBConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                TeslimEdilenlerModel d = new TeslimEdilenlerModel();
                d.setMusteriBilgiler(resultSet.getString("musteriadi")+ " " + resultSet.getString("musterisoyadi"));
                d.setUrunBilgileri(resultSet.getString("urunadi"));
                d.setID(resultSet.getString("siparisid"));
                modelTeslimEdilenler.add(d);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(statement != null){
                statement.close();
            }
            if(conn != null){
                conn.close();
            }
        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.refresh:
                adapter.updateResults(modelTeslimEdilenler);
                Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deliverables,menu);
        return true;
    }
}
