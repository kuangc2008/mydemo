
package com.example.demo.feb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import com.example.demo.R;
import com.example.demo.feb.StackOverflowXmlParser.Entry;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class NetworkActivity extends Activity {
    public static final String WIFI = "wi-fi";
    public static final String ANY = "any";

    private NetworkReceiver receiver = new NetworkReceiver();
    private static String sPref = null;
    public static boolean refreshDisplay = true;
    
    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;
    private static final String URL =
            "http://stackoverflow.com/feeds/tag?tagnames=android&sort=newest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, filter);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onStart() {
        Log.v("kc", "onStart");
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sPref = sharedPref.getString("listPref", WIFI);
        //每次进入的时候，更新下，链接的flag
        updateConnectedFlags();
        if(refreshDisplay) {
            loadPage();
        }
    }

    private void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager) 
               getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

    private void loadPage() {
        if((sPref.equals(ANY) && (wifiConnected || mobileConnected))
                ||((sPref.equals(WIFI)) && (wifiConnected))) {
            new DownloadXmlTask().execute(URL);
        } else {
            showErrorPage();
        }
    }
    
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                Log.v("kc", "error", e);
                return "网络链接错误";
            } catch (XmlPullParserException e) {
                Log.v("kc", "error", e);
                return "xml解析错误";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            setContentView(R.layout.network_acitivyt);
            // Displays the HTML string in the UI via a WebView
            WebView myWebView = (WebView) findViewById(R.id.webview);
            myWebView.loadData(result, "text/html", null);
        }
    }
    
    private String loadXmlFromNetwork(String urlString) throws IOException, XmlPullParserException {
        InputStream stream = null;
        StackOverflowXmlParser parser = new StackOverflowXmlParser();
        List<Entry> entrys = null;
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fommater = new SimpleDateFormat("MMM dd h:mmaa");
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pref = sharedPrefs.getBoolean("summaryPref", true);
        
        StringBuilder htmlString = new StringBuilder();
        htmlString.append("<h3>" + "标题" + "</h3>");
        htmlString.append("<em>" + "更新" + " " + 
                fommater.format(rightNow.getTime()) + "</em>");
        
        try {
            stream = downloadUrl(urlString);
            entrys = parser.parse(stream);
        } finally {
            if(stream != null) {
                stream.close();
            }
        }
        
        for(Entry entry : entrys) {
            htmlString.append("<p><a href='");
            htmlString.append(entry.link);
            htmlString.append("'>" + entry.title + "</a></p>");
            if(pref) {
                htmlString.append(entry.summary);
            }
        }
  
        return htmlString.toString();
    }
    
    private InputStream downloadUrl(String urlString) throws IOException {
        java.net.URL url = new java.net.URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }
    
    private void showErrorPage() {
        setContentView(R.layout.network_acitivyt);
        // The specified network connection is not available. Displays error message.
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadData(getResources().getString(R.string.hello_world),
                "text/html", null);
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("kc", "onRecevier action-->" + intent.getAction());
            Bundle extras = intent.getExtras();
            Set<String> set = extras.keySet();
            for (String key : set) {
                Log.v("kc", "key value-->" + key + "  ---  " + extras.get(key));
            }
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (WIFI.equals(sPref) && networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                refreshDisplay = true;
                Toast.makeText(context, "已链接wifi", 1000).show();
            } else if (ANY.equals(sPref) && networkInfo != null) {
                refreshDisplay = true;
            } else {
                refreshDisplay = false;
                Toast.makeText(context, "没有网络链接", Toast.LENGTH_SHORT).show();
            }
        }

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "refresh");
        menu.add(0, 1, 0, "setting");
        return true;
    }

    // Handles the user's menu selection.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
                Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(settingsActivity);
                return true;
        case 0:
                loadPage();
                return true;
        default:
                return super.onOptionsItemSelected(item);
        }
    }
}
