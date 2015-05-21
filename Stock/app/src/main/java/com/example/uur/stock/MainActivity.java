package com.example.uur.stock;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends ActionBarActivity {
    SlidingMenu menu;
    MediaPlayer clickSound;
    Button buttonMenuProfil, buttonMenuDagitilacalar,
            buttonMenuDagitilanlar,buttonMenuDagitilamayanlar,
            buttonMenuAyarlar,buttonMenuSync,buttonMenuKapat;
    RelativeLayout profil,slMenuHome,slMenuTeslimEdilecekler,
        slMenuTeslimEdilenler,slMenuTeslimEdilemeyenler,slMenuNotlar,
        slMenuHatirlatma,slMenuExit,slMenuFindAddress;
    TextView title, desc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sliding_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.next);

        init();
        GetDataFromLoginActivity();
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ProfilActivity.class);
                i.putExtra("Email",desc.getText().toString().trim());
                startActivity(i);
            }
        });
        slMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        slMenuTeslimEdilecekler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DeliverablesActivitiy.class);
                startActivity(i);
            }
        });

        slMenuTeslimEdilenler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TeslimEdilenlerActivitiy.class);
                startActivity(i);
            }
        });

        slMenuTeslimEdilemeyenler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TeslimEdilemeyenlerActivity.class);
                startActivity(i);
            }
        });

        slMenuNotlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NotlarActivity.class);
                startActivity(i);
            }
        });

        slMenuHatirlatma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,HatirlatmaActivity.class);
                startActivity(i);
            }
        });

        slMenuExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();

            }
        });
        slMenuFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndroidGPSTrackingActivity.class);
                startActivity(intent);
            }
        });
        buttonMenuProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
        buttonMenuDagitilacalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeliverablesActivitiy.class);
                startActivity(intent);
            }
        });
        buttonMenuDagitilanlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeslimEdilenlerActivitiy.class);
                startActivity(intent);
            }
        });
        buttonMenuDagitilamayanlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeslimEdilemeyenlerActivity.class);
                startActivity(intent);
            }
        });
        buttonMenuAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        buttonMenuSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///
            }
        });
        buttonMenuKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    private void GetDataFromLoginActivity(){
        Intent intent = getIntent();
        String getName = intent.getStringExtra("Adi");
        String getSurname =  intent.getStringExtra("Soyadi");
        desc.setText(intent.getStringExtra("Email"));
        title.setText(getName + " " + getSurname);
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
    @Override
    public void onBackPressed() {
        if ( menu.isMenuShowing()) {
            menu.toggle();
            this.getSupportActionBar().setHomeAsUpIndicator( R.drawable.back);

        }
        else {

            super.onBackPressed();
            this.getSupportActionBar().setHomeAsUpIndicator( R.drawable.next);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
           // this.getSupportActionBar().setHomeAsUpIndicator( R.drawable.back);
            this.menu.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.menu.toggle();
                //this.getSupportActionBar().setHomeAsUpIndicator( R.drawable.back);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showCustomDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        TextView txtTitle = (TextView) dialog.findViewById(R.id.txt_dialog_title);
        txtTitle.setText("Exit Dialog");

        TextView txtMessage = (TextView) dialog.findViewById(R.id.txt_dialog_message);
        txtMessage.setText("Çıkmak istediğinize emin misiniz?");

        Button btnExit = (Button) dialog.findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the browser
                Intent browserIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(browserIntent);
                clickSound.start();
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void init(){
        buttonMenuProfil = (Button)findViewById(R.id.buttonMenuProfil);
        buttonMenuDagitilacalar = (Button)findViewById(R.id.buttonMenuDagitilacaklar);
        buttonMenuDagitilanlar = (Button)findViewById(R.id.buttonMenuDagitilanlar);
        buttonMenuDagitilamayanlar = (Button)findViewById(R.id.buttonMenuDagitilamayanlar);
        buttonMenuAyarlar = (Button)findViewById(R.id.buttonMenuAyarlar);
        buttonMenuKapat = (Button)findViewById(R.id.buttonMenuKapat);
        buttonMenuSync = (Button)findViewById(R.id.buttonMenuSync);

        profil = (RelativeLayout)findViewById(R.id.rlProfil);
        slMenuHome = (RelativeLayout)findViewById(R.id.rlHome);
        slMenuTeslimEdilecekler = (RelativeLayout)findViewById(R.id.rlSubscriptions);
        slMenuTeslimEdilenler = (RelativeLayout)findViewById(R.id.rlPlaylists);
        slMenuTeslimEdilemeyenler = (RelativeLayout)findViewById(R.id.rlHistory);
        slMenuNotlar = (RelativeLayout)findViewById(R.id.rlNote);
        slMenuHatirlatma = (RelativeLayout)findViewById(R.id.rlReminder);
        slMenuExit = (RelativeLayout)findViewById(R.id.rlExit);
        slMenuFindAddress = (RelativeLayout)findViewById(R.id.rlFindAddress);
        clickSound = MediaPlayer.create(getApplicationContext(),R.raw.clicksound);

        // getting data for sliding menu profil place
        title = (TextView)findViewById(R.id.title);
        desc = (TextView)findViewById(R.id.desc);

    }
}