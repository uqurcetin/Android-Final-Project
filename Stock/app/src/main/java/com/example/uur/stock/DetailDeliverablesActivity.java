package com.example.uur.stock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Uğur on 19.5.2015.
 */
public class DetailDeliverablesActivity extends ActionBarActivity {
    private final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.24:1433;DatabaseName=DB_FinalProject";
    private final String DB_USER ="Testing";
    private final String DB_PASSWORD = "sa123";

    EditText edtSiparisID,edtUrunAdi,edtMusteriAdi,edtOdemeTuru,edtTeslimTarihi,edtSiparisAdresi,edtTeslimEden,edtAciklama;
    Spinner spSiparisDurum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaildeliverables);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        init();
        Intent i = getIntent();
        String a = i.getStringExtra("id");
        edtSiparisID.setText(a);
    }
    private void init(){
        edtSiparisID = (EditText)findViewById(R.id.edtSiparisID);
        spSiparisDurum = (Spinner) findViewById(R.id.spSiparisDurum);
        edtUrunAdi = (EditText)findViewById(R.id.edtUrunAdi);
        edtMusteriAdi = (EditText) findViewById(R.id.edtMusteriAdi);
        edtOdemeTuru = (EditText)findViewById(R.id.edtOdemeTuru);
        edtTeslimTarihi = (EditText)findViewById(R.id.edtTeslimTarihi);
        edtSiparisAdresi = (EditText)findViewById(R.id.edtSiparisAdresi);
        edtTeslimEden=(EditText)findViewById(R.id.edtTeslimEden);
        edtAciklama = (EditText)findViewById(R.id.edtAciklama);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detaildeliverables, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.save:
                 try{
                      updateOrderState();
                      Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
    private void updateOrderState() throws  SQLException {
        Connection conn = null;
        Statement statement= null;
        ResultSet resultSet = null;
        String query = "UPDATE Siparisler SET SiparisDurumu='"+spSiparisDurum.getSelectedItem().toString()+
                "' WHERE SiparisID="+edtSiparisID.getText().toString()+"";
        try{
            conn = getDBConnection();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet=statement.executeQuery(query);

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(statement !=null){
                statement.close();
            }if(conn!=null){
                conn.close();
            }
        }
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
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

}