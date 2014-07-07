package com.example.next_year2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/6/30.
 */
public class WebViewTestActivity extends Activity implements View.OnClickListener {
    WebView mWebView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        Button button = (Button) findViewById(R.id.add_page);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new MyWebViewClient());
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mWebView.loadUrl("http://www.ganji.com/");
    }
}
