package com.ilikegap.app;

import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import im.delight.android.webview.AdvancedWebView;



/**
 * Created by Inzimam Tariq on 18-Oct-17.
 */

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener {
    
    private AdvancedWebView mWebView;
    private Utils utils;
    private String url = "https://www.ilikegap.com";
    private ProgressDialog progressBar;//progress bar
    public boolean isFirstStart;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Intro App Initialize SharedPreferences
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

                //  Check either activity or app is open very first time or not and do action
                if (isFirstStart) {

                    //  Launch application introduction screen
                    Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();

        this.utils = new Utils(this);
        progressBar = ProgressDialog.show(this, "Harap menunggu", "Mengambil Data...");//progress bar
        
        if (utils.isNetworkConnected()) {
            mWebView = findViewById(R.id.mWebView);
            mWebView.setListener(this, this);
            mWebView.loadUrl(url);
        } else {
            utils.showAlertDialoge();
            //mWebView.loadUrl("file:///android_asset/sample.html");
        }
        
    }
    
    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        
        if (utils.isNetworkConnected()) {
            mWebView.onResume();
        } else {
            utils.showAlertDialoge();
        }
        // ...
    }
    
    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        if (utils.isNetworkConnected()) {
            mWebView.onPause();
        } else {
            utils.showAlertDialoge();
        }
        // ...
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (utils.isNetworkConnected()) {
            progressBar.cancel();
            mWebView.onActivityResult(requestCode, resultCode, intent);
        } else {
            utils.showAlertDialoge();
        }
        // ...
    }
    
    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }
    
    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        progressBar.show();
    }
    
    @Override
    public void onPageFinished(String url) {
        progressBar.cancel();
    }
    
    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        progressBar.cancel();
        utils.showAlertDialoge("Error Loading Data!");
    }
    
    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }
    
    @Override
    public void onExternalPageRequest(String url) {
    }
    
}
