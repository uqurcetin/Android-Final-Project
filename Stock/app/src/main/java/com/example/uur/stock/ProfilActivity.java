package com.example.uur.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Uğur on 13.5.2015.
 */
public class ProfilActivity extends ActionBarActivity
{
    TextView userStatus,txtTel,txtMail,txtUserName,txtPassword,lblWorkStatus,txtDTariDYerIsBas,txtAdres;
    ToggleButton tbWorkStatus;
    Intent intent;
    private static final String TAG = "Error";

    private final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.24:1433;DatabaseName=DB_FinalProject";
    private final String DB_USER ="Testing";
    private final String DB_PASSWORD = "sa123";

    private Connection getDBConnection()
    {
        Connection conn = null;
        try{
            Class.forName(DB_DRIVER);

        }catch(ClassNotFoundException e){
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
    private void getUserInformationFromDB() throws SQLException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        intent = getIntent();
        String emailForQuery = intent.getStringExtra("Email");
        String query = "Select * From Kullanicilar Where Email='"+emailForQuery+"'";
        try{
            conn = getDBConnection();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultSet =statement.executeQuery(query);
            while(resultSet.next()){

                String getName  = resultSet.getString("Adi");
                String getSurname = resultSet.getString("Soyadi");
                String getTelefon = resultSet.getString("Telefon");
                String getUserName = resultSet.getString("KullaniciAdi");
                String getPassword = resultSet.getString("Sifre");
                String getMail = resultSet.getString("Email");
                int getWorkStatus = resultSet.getInt("CalismaDurumu");
                String getAdres = resultSet.getString("Adresi");
                String getDogumTarihi = resultSet.getString("DogumTarihi");
                String getDogumyeri = resultSet.getString("DogumYeri");
                String getIseBaslangic = resultSet.getString("BaslangicTarihi");


                userStatus.setText(getName + " " + getSurname);
                txtTel.setText(getTelefon);
                txtUserName.setText(getUserName);
                txtPassword.setText(getPassword);
                txtMail.setText(getMail);
                tbWorkStatus.setChecked(setToggleButtonStatus(getWorkStatus));
                txtAdres.setText(getAdres);
                txtDTariDYerIsBas.setText(getDogumTarihi + " | " + getDogumyeri + " | " + getIseBaslangic);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(statement != null){
                statement.close();
            }if(conn != null){
                conn.close();
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        try
        {
            getUserInformationFromDB();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }

        tbWorkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    lblWorkStatus.setText("Çalışıyor");
                }else{
                    lblWorkStatus.setText("Çalışmıyor");
                }
            }
        });
    }

    private boolean setToggleButtonStatus(int getWorkStatus){
        if(getWorkStatus==1) {
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    Log.i(TAG, "You have forgotten to specify the parentActivityName in the AndroidManifest!");
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void init()
    {
        userStatus = (TextView)findViewById(R.id.userStatus);
        txtTel = (TextView)findViewById(R.id.txtTel);
        txtUserName = (TextView)findViewById(R.id.txtusername);
        txtPassword = (TextView)findViewById(R.id.txtPassword);
        txtMail = (TextView)findViewById(R.id.txtMail);
        tbWorkStatus =(ToggleButton)findViewById(R.id.tbWorkStatus);
        lblWorkStatus = (TextView)findViewById(R.id.lblWorkStatus);
        txtDTariDYerIsBas = (TextView)findViewById(R.id.txtDTariDYerIsBas);
        txtAdres = (TextView)findViewById(R.id.txtAdres);
    }
}
