package com.example.uur.stock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uur.stock.Model.DeliverablesModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uğur on 19.5.2015.
 */
public class DetailDeliverablesActivity extends ActionBarActivity {

    private final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    private final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.24:1433;DatabaseName=DB_FinalProject";
    private final String DB_USER ="Testing";
    private final String DB_PASSWORD = "sa123";

    String IDValue;

    EditText edtSiparisID,edtUrunAdi,edtMusteriAdi,edtOdemeTuru,edtTeslimTarihi,edtSiparisAdresi,edtTeslimEden,edtAciklama,edtUrunMiktari,edtSevkiyatTarihi;
    Spinner spSiparisDurum;
    private List<DeliverablesModel> deliverablesModelList = new ArrayList<DeliverablesModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaildeliverables);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        init();

        Intent intent = getIntent();
        IDValue = intent.getStringExtra("id");
        edtSiparisID.setText(IDValue);

        try{
            initOrderInformation();
        }catch (SQLException e){
            Log.e("HATA !!!",e.toString());
        }

    }
    private void initOrderInformation() throws  SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            String getOrderRecordQuery =
            "SELECT S.Miktar as miktar, S.Aciklama as aciklama, CASE WHEN S.OdemeTuru=0 THEN 'Peşin Ödeme' WHEN S.OdemeTuru=1 THEN 'Taksitli' WHEN S.OdemeTuru=2 THEN 'Kapıda Ödeme' ELSE 'Belirtilmemiş' END AS tur, " +
            "S.SevkiyatTarih as sevkiyattar, " +
            "S.TeslimatTarihi as teslimattar, K.Adi as adi, K.Soyadi as soyadi, M.MusteriAdi as musteriadi, M.MusteriSoyadi as musterisoyadi," +
            "M.musteriAdresi as musteriadresi, U.UrunAdi as urunadi FROM Sevkiyatlar S  " +
            "INNER JOIN Musteriler M ON M.MusteriID=S.MusteriID INNER JOIN Urunler U ON U.UrunID=S.urunID " +
            "INNER JOIN Kullanicilar K ON K.KullaniciID=S.TeslimatiYapan WHERE S.SiparisID="+IDValue+"";

            conn=getDBConnection();
            statement =conn.createStatement();
            resultSet=statement.executeQuery(getOrderRecordQuery);

            while(resultSet.next())
            {
                edtUrunAdi.setText(resultSet.getString("urunadi"));
                edtMusteriAdi.setText(resultSet.getString("musteriadi") + " " + resultSet.getString("musterisoyadi"));
                edtOdemeTuru.setText(resultSet.getString("tur"));
                edtTeslimTarihi.setText(resultSet.getString("teslimattar"));
                edtSiparisAdresi.setText(resultSet.getString("musteriadresi"));
                edtTeslimEden.setText(resultSet.getString("adi") + " " + resultSet.getString("soyadi"));
                edtAciklama.setText(resultSet.getString("aciklama"));
                edtSevkiyatTarihi.setText(resultSet.getString("sevkiyattar"));
                edtUrunMiktari.setText(resultSet.getString("miktar"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(statement !=null){
                statement.close();
            }if(conn != null){
                conn.close();
            }
        }
    }
    private void init(){
        edtUrunMiktari = (EditText)findViewById(R.id.edtUrunMiktari);
        edtSevkiyatTarihi = (EditText)findViewById(R.id.edtSevkiyatTarihi);
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
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                    AlertDialog.Builder builder= new AlertDialog.Builder(this);
                    builder.setTitle("Bildirim").setMessage("Sipariş Teslim Edildi").setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Kapat",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }) .setNegativeButton("İptal",null)
                           .show();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
    private void updateOrderState() throws  SQLException {
        Connection conn = null;
        Statement statement= null;
        String query = "UPDATE Sevkiyatlar " +
                "SET SevkiyatDurumu='"+spSiparisDurum.getSelectedItem().toString()+"'" +
                " WHERE SiparisID="+edtSiparisID.getText().toString()+" " +
                "UPDATE Siparisler " +
                "SET SiparisDurumu='"+spSiparisDurum.getSelectedItem().toString()+"' " +
                "WHERE SiparisID="+edtSiparisID.getText().toString()+"";

        ResultSet resultSet = null;
        String caseOfWhere = "Ürün teslime hazır";
        try{
            conn = getDBConnection();
            statement = conn.createStatement();
            statement.executeUpdate(query);

            String sql = "SELECT S.SiparisID, M.MusteriAdi, U.UrunAdi FROM Sevkiyatlar S " +
                    "INNER JOIN Musteriler M ON M.MusteriID=S.MusteriID " +
                    "INNER JOIN Urunler U ON U.UrunID=S.urunID WHERE S.SiparisDurumu='"+caseOfWhere+"'";
            resultSet = statement.executeQuery(sql);

            while(resultSet.next())
            {
                DeliverablesModel d = new DeliverablesModel();
                d.setMusteriBilgiler(resultSet.getString("MusteriAdi"));
                d.setUrunBilgileri(resultSet.getString("UrunAdi"));
                d.setID(resultSet.getString("SiparisID"));
                deliverablesModelList.add(d);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally
        {
            if(statement !=null)
            {
                statement.close();
            }
            if(conn!=null)
            {
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