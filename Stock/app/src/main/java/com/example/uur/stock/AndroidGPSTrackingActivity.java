package com.example.uur.stock;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Uğur on 17.5.2015.
 */
public class AndroidGPSTrackingActivity extends ActionBarActivity {
    Button btnShowLocation;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstracking);
        this.getSupportActionBar().setHomeAsUpIndicator( R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B0B0B")));

        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_location,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.get_location:
                gps = new GPSTracker(AndroidGPSTrackingActivity.this);
                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Toast.makeText(getApplicationContext(),"Lokasyon" + latitude +"\n "+ longitude,Toast.LENGTH_SHORT).show();
                }else{
                    gps.showSettingsAlert();
                }
                return true;
            case R.id._connect:
                Intent intent = new Intent(AndroidGPSTrackingActivity.this, LocationActivity.class);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"Servis çalışyor",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dis_connect:
                Toast.makeText(getApplicationContext(),"Servis durdu",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
