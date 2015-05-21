package com.example.uur.stock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Uğur on 18.5.2015.
 */
public class SignUpActivity extends ActionBarActivity{

    static EditText etEmail,etUserName,etPass,etName,etSurname;
    static Button btnSingIn;
    static boolean checkUserInDB=false;

    private static final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.24:1433;DatabaseName=DB_FinalProject";
    private static final String DB_USER ="Testing";
    private static final String DB_PASSWORD = "sa123";


    private static void checkOfUser() throws  SQLException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "Select KullaniciAdi, Sifre from Kullanicilar Where KullaniciAdi='"
                +etUserName.getText().toString().trim()+"' AND Sifre='"+etPass.getText().toString().trim()+"'";

        try{
            conn = getDBConnection();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);

            if(resultSet.isBeforeFirst())
            {
                // eğer veri tabanında etUserName ve etPass ile eşleşen kayıtvarsa kayıt ettirmiyoruz.
                checkUserInDB=true;
            }else{
                checkUserInDB=false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(statement !=null){
                statement.close();
            }
            if(conn != null){
                conn.close();
            }
        }
    }
    private static void insertRecordIntoUsersTable  () throws SQLException {
        if(!checkUserInDB) // false ise DB de kayıt yokttur ve kayıt edilmeye müsaittir
        {
            Connection conn = null;
            PreparedStatement preparedStatement = null;

            String query = "INSERT INTO Kullanicilar" +
                    "(KullaniciAdi,Sifre,Adi,Soyadi,Email) VALUES (?,?,?,?,?)";
            try {
                conn = getDBConnection();
                preparedStatement = conn.prepareStatement(query);

                preparedStatement.setString(1, etUserName.getText().toString().trim());
                preparedStatement.setString(2, etPass.getText().toString().trim());
                preparedStatement.setString(3, etName.getText().toString().trim());
                preparedStatement.setString(4, etSurname.getText().toString().trim());
                preparedStatement.setString(5, etEmail.getText().toString().trim());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }else{
            //  kayıt var diye.
        }
    }
    private  static Connection getDBConnection(){
        Connection conn = null;
        try{
            Class.forName(DB_DRIVER);

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
            return conn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        init();
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(etUserName) || isEmpty(etPass) || isEmpty(etName)|| isEmpty(etSurname) || isEmpty(etEmail)) {
                    Toast.makeText(getApplicationContext(),"Bilgiler Hatalı veya Eksik" ,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try{
                        checkOfUser();
                        insertRecordIntoUsersTable();
                        if(!checkUserInDB) {
                            Toast.makeText(getApplicationContext(), "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Bu bilgilere ait kayıt bulundu", Toast.LENGTH_SHORT).show();
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    private  void init(){
       etEmail = (EditText)findViewById(R.id.etEmail);
       etUserName = (EditText)findViewById(R.id.etUserName);
       etPass = (EditText)findViewById(R.id.etPass);
       etName = (EditText)findViewById(R.id.etName);
       etSurname = (EditText)findViewById(R.id.etSurname);
       btnSingIn = (Button) findViewById(R.id.btnSingIn);
    }
    @Override
    protected void onPause() {
       super.onPause();
       overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
   }
}
