package com.example.uur.stock;


import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends ActionBarActivity {
    Button btnLogin;
    EditText edtUserName,edtPassword;
    Intent intent;
    TextView txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        Init();

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isEmpty(edtUserName) || (isEmpty(edtPassword))) {
                    Toast.makeText(getApplicationContext(), "Hatalı Giriş", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String KullaniciAdi = edtUserName.getText().toString().trim();
                    String Parola = edtPassword.getText().toString().trim();
                    try
                    {
                        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                        ResultSet resultSet = null;
                        Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.24:1433;DatabaseName=DB_FinalProject", "Testing", "sa123");
                        Statement statement;
                        statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String sorgu = "SELECT * from Kullanicilar WHERE KullaniciAdi ='" +KullaniciAdi+"' AND Sifre='"+Parola+"'";
                        resultSet = statement.executeQuery(sorgu);

                        if(resultSet.isBeforeFirst())
                        {
                            while (resultSet.next())
                            {
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("Adi", resultSet.getString("Adi"));
                                intent.putExtra("Soyadi", resultSet.getString("Soyadi"));
                                intent.putExtra("Email", resultSet.getString("Email"));
                                Toast.makeText(getApplicationContext(), KullaniciAdi + " " + Parola + " doğru ", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Bilgiler Hatalı",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Init(){
        btnLogin=(Button)findViewById(R.id.btnLogin);
        edtUserName=(EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPass);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

}
