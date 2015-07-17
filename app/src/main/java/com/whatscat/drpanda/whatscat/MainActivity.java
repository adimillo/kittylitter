package com.whatscat.drpanda.whatscat;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;


public class MainActivity extends ActionBarActivity {

    private static final String CAT_HASHTAG = " #WhatsCat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        mShareActionProvider.setShareIntent((createShareCatIntent()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            //shareIntent.setType("text/plain");
            shareIntent.setType("image/jpeg");
            shareIntent.putExtra(Intent.EXTRA_TEXT, CAT_HASHTAG);
            shareIntent.putExtra(Intent.EXTRA_STREAM, "http://www.vetprofessionals.com/catprofessional/images/home-cat.jpg");

            startActivity(shareIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareCatIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //shareIntent.setType("text/plain");
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_TEXT, CAT_HASHTAG);
        shareIntent.putExtra(Intent.EXTRA_STREAM, "http://www.vetprofessionals.com/catprofessional/images/home-cat.jpg");


        return shareIntent;
    }
}
