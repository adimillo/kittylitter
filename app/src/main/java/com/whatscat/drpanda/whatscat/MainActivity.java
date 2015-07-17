package com.whatscat.drpanda.whatscat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;

//import android.widget.ShareActionProvider;


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
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.sad_cat);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
            Uri imageUri = Uri.parse(path);

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select"));

        }

        if (id == R.id.menu_item_bookmark) {

        }

        if (id == R.id.menu_item_show_bookmarks) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragmentManager.findFragmentById(R.id.fragment));
            transaction.add(fragmentManager.findFragmentById(R.id.fragment_bookmarks), "tag");
//            transaction.replace(R.id.fragment, new BookmarksFragment());
            transaction.addToBackStack(null);
            transaction.commit();
            String state = Environment.getExternalStorageState();
//            setContentView(R.id.fragment_bookmarks);
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareCatIntent() {

//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.sad_cat);
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
//        Uri imageUri = Uri.parse(path);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, ""); //imageUri);

        return share;
    }
}
