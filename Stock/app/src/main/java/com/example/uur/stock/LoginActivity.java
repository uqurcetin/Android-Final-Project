package com.example.uur.stock;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {
    Button btnLogin;
    EditText edtUserName,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(edtUserName) || (isEmpty(edtPassword))){
                    Toast.makeText(getApplicationContext(),"Hatalı Giriş",Toast.LENGTH_SHORT).show();
                }else{
                    final Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    /// JSON ile datayı çekip uygun kullanıcı ile giriş yaptırılacak...
                }
            }
        });

    }
    private void Init(){
        btnLogin=(Button)findViewById(R.id.btnLogin);
        edtUserName=(EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPass);
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
