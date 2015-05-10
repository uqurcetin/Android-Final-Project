package com.example.uur.stock;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity {

    SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSlidingActionBarEnabled(false);
        setBehindContentView(R.layout.user_profile);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sliding_menu);
        ActionBar actionBarActivity = getActionBar();
        actionBarActivity.setDisplayHomeAsUpEnabled(true);

    }



    @Override
    public void onBackPressed() {
        if ( menu.isMenuShowing()) {
            menu.toggle();
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}