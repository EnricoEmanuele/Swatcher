package sweng.swatcher.model;

import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ee on 07/10/16.
 */

public class StreamRequest extends HttpRequest {

    private WebView webview;

    public StreamRequest(String ipAddress, String port, Authorization authorization, WebView webview) {
        super(ipAddress,port,authorization);
        this.webview = webview;
        webview.setWebViewClient(new Streaming(authorization));
    }

    public int sendRequest(){
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl(getURL());
        return 0;
    }

    public String getURL(){
        return "http://"+super.getIpAddress()+":"+super.getPort();
    }

    class Streaming extends WebViewClient {

        private Authorization authorization;

        public Streaming(Authorization authorization){
            this.authorization = authorization;
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            handler.proceed(authorization.getUsername(), authorization.getPassword());
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

}
