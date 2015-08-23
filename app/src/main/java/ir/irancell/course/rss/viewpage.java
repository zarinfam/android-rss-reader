package ir.irancell.course.rss;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by azadeh.a on 8/19/2015.
 */

    public class viewpage extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.viewpage);
            WebView webview=(WebView)findViewById(R.id.webView);
            webview.loadUrl("http://www.asriran.com");

        }
    }


