package sweng.swatcher.command;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import sweng.swatcher.model.Authorization;
import sweng.swatcher.request.HttpRequest;
import sweng.swatcher.util.MediaButtonSet;

/**
 * Created by ee on 19/10/16.
 */

public class StreamCommand implements CommandInterface {

    private WebView webView;
    private View view;
    private HttpRequest httpRequest;
    private MediaButtonSet mediaButtonSet;

    public StreamCommand(WebView webView, View view, HttpRequest httpRequest, MediaButtonSet mediaButtonSet) {
        this.webView = webView;
        this.httpRequest = httpRequest;
        this.view = view;
        this.mediaButtonSet = mediaButtonSet;
    }

    public void execute(){


        webView.setWebViewClient(new Streaming(httpRequest.getAuthorization()));
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(httpRequest.getURL());

        //Snackbar.make(view, "Successful Connected", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        displayMediaButton();

    }

    private void displayMediaButton(){
        mediaButtonSet.hideAllFromStopList();
        mediaButtonSet.displayAllFromPlayList();
    }

    public void hideMediaButton(){
        mediaButtonSet.hideAllFromPlayList();
        mediaButtonSet.displayAllFromStopList();
        Snackbar.make(view, "Stop streaming video", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        webView.loadUrl("file:///android_asset/streamingStop.html");
    }


    class Streaming extends WebViewClient {

        private Authorization authorization;

        public Streaming(Authorization authorization){
            this.authorization = authorization;
        }


        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            Log.i("PROCEED", realm+" - "+host+" - ");
            handler.proceed(authorization.getUsername(), authorization.getPassword());
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        //Streaming Port Error
        //IP-Address Error (timeout)
        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i("GENERIC-ERROR", errorCode+" "+description);
            Snackbar.make(view, "Error: "+errorCode+" "+description, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            super.onReceivedError(view,errorCode,description,failingUrl);
        }

        //Credentials Error
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Log.i("HTTP-ERROR", errorResponse.getStatusCode()+"");
            //Snackbar.make(view, "Error: "+errorResponse.getStatusCode()+" "+errorResponse.getReasonPhrase(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

    public WebView getWebView() {
        return webView;
    }

    public View getView() {
        return view;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public MediaButtonSet getMediaButtonSet() {
        return mediaButtonSet;
    }

}
