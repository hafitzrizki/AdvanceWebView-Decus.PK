package pk.decus.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import im.delight.android.webview.AdvancedWebView;


/**
 * Created by Inzimam Tariq on 18-Oct-17.
 */

public class MainActivity extends Activity implements AdvancedWebView.Listener {
    
    private AdvancedWebView mWebView;
    private Utils utils;
    private String url = "https://www.decus.pk";
    private ProgressDialog progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.utils = new Utils(this);
        progressBar = ProgressDialog.show(this, "Progress", "Loading Data...");
        
        if (utils.isNetworkConnected()) {
            mWebView = findViewById(R.id.mWebView);
            mWebView.setListener(this, this);
            mWebView.loadUrl(url);
        } else {
            utils.showAlertDialoge();
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
