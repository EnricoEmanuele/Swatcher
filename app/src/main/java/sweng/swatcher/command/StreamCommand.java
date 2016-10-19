package sweng.swatcher.command;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import sweng.swatcher.R;
import sweng.swatcher.model.Authorization;
import sweng.swatcher.request.HttpRequest;
import sweng.swatcher.request.StreamRequest;
import sweng.swatcher.util.MediaButtonSet;

/**
 * Created by ee on 19/10/16.
 */

public class StreamCommand implements CommandInterface {

    private WebView webView;
    private View view;
    private HttpRequest request;
    private MediaButtonSet mediaButtonSet;

    public StreamCommand(WebView webView,View view, HttpRequest request, MediaButtonSet mediaButtonSet) {
        this.webView = webView;
        this.request = request;
        this.view = view;
        this.mediaButtonSet = mediaButtonSet;
    }

    public void execute(){
        webView.setWebViewClient(new Streaming(request.getAuthorization()));
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(request.getURL());

        Snackbar.make(view, "Successful Connected", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        displayMediaButton();

    }

    private void displayMediaButton(){
        mediaButtonSet.hideAllFromStopList();
        mediaButtonSet.displayAllFromPlayList();
    }

    public void hideMediaButton(){
        mediaButtonSet.hideAllFromPlayList();
        mediaButtonSet.displayAllFromStopList();
        Snackbar.make(view, "Stop streaming video", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        webView.loadUrl("file:///android_asset/streamingStop.html");
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
