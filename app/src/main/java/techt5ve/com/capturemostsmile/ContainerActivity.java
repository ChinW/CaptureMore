package techt5ve.com.capturemostsmile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ContainerActivity extends AppCompatActivity {

    private static final String URL = "http://python.dog/";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mWebView = (WebView) findViewById(R.id.webView);

        WebViewClient client = new WebViewClient();
        mWebView.loadUrl(URL);
    }
}
