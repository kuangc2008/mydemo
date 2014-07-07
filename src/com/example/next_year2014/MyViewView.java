package com.example.next_year2014;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.Method;
import java.util.Map;

public class MyViewView extends WebView {
    @Override
    public void setLayerType(int layerType, Paint paint) {
        Log.v("kc", "setLayType");
        super.setLayerType(layerType, paint);
    }

    public MyViewView(Context context) {
        super(context);
        Log.v("kc", "MyViewView");
    }

    public MyViewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v("kc", "MyViewView");
    }

    public MyViewView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.v("kc", "MyViewView");
    }

    public MyViewView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
        Log.v("kc", "MyViewView");
    }

    @Override
    public void setHorizontalScrollbarOverlay(boolean overlay) {
        super.setHorizontalScrollbarOverlay(overlay);
        Log.v("kc", "setHorizontalScrollbarOverlay");
    }

    @Override
    public void setVerticalScrollbarOverlay(boolean overlay) {
        super.setVerticalScrollbarOverlay(overlay);
        Log.v("kc", "setVerticalScrollbarOverlay");
    }

    @Override
    public boolean overlayHorizontalScrollbar() {
        Log.v("kc", "overlayHorizontalScrollbar");
        return super.overlayHorizontalScrollbar();

    }

    @Override
    public boolean overlayVerticalScrollbar() {
        Log.v("kc", "overlayVerticalScrollbar");
        return super.overlayVerticalScrollbar();
    }

    @Override
    public SslCertificate getCertificate() {
        Log.v("kc", "getCertificate");
        return super.getCertificate();
    }

    @Override
    public void setCertificate(SslCertificate certificate) {
        super.setCertificate(certificate);
    }

    @Override
    public void savePassword(String host, String username, String password) {
        Log.v("kc", "savePassword");
        super.savePassword(host, username, password);
    }

    @Override
    public void setHttpAuthUsernamePassword(String host, String realm, String username, String password) {
        Log.v("kc", "setHttpAuthUsernamePassword");
        super.setHttpAuthUsernamePassword(host, realm, username, password);
    }

    @Override
    public String[] getHttpAuthUsernamePassword(String host, String realm) {
        Log.v("kc", "getHttpAuthUsernamePassword");
        return super.getHttpAuthUsernamePassword(host, realm);
    }

    @Override
    public void destroy() {
        Log.v("kc", "destroy");
        super.destroy();
    }

    @Override
    public void setNetworkAvailable(boolean networkUp) {
        Log.v("kc", "setNetworkAvailable");
        super.setNetworkAvailable(networkUp);
    }

    @Override
    public WebBackForwardList saveState(Bundle outState) {
        Log.v("kc", "saveState");
        return super.saveState(outState);
    }

    @Override
    public WebBackForwardList restoreState(Bundle inState) {
        Log.v("kc", "restoreState");
        return super.restoreState(inState);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        Log.v("kc", "loadUrl");
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        Log.v("kc", "loadUrl");
        super.loadUrl(url);
    }

    @Override
    public void postUrl(String url, byte[] postData) {
        Log.v("kc", "postUrl");
        super.postUrl(url, postData);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        Log.v("kc", "loadData");
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String failUrl) {
        Log.v("kc", "loadDataWithBaseURL");
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, failUrl);
    }

    @Override
    public void saveWebArchive(String filename) {
        Log.v("kc", "saveWebArchive");
        super.saveWebArchive(filename);
    }

    @Override
    public void saveWebArchive(String basename, boolean autoname, ValueCallback<String> callback) {
        Log.v("kc", "saveWebArchive");
        super.saveWebArchive(basename, autoname, callback);
    }

    @Override
    public void stopLoading() {
        Log.v("kc", "stopLoading");
        super.stopLoading();
    }

    @Override
    public void reload() {
        Log.v("kc", "reload");
        super.reload();
    }

    @Override
    public boolean canGoBack() {
        Log.v("kc", "canGoBack");
        return super.canGoBack();
    }

    @Override
    public void goBack() {
        Log.v("kc", "goBack");
        super.goBack();
    }

    @Override
    public boolean canGoForward() {
        Log.v("kc", "canGoForward");
        return super.canGoForward();
    }

    @Override
    public void goForward() {
        Log.v("kc", "goForward");
        super.goForward();
    }

    @Override
    public boolean canGoBackOrForward(int steps) {
        Log.v("kc", "canGoBackOrForward");
        return super.canGoBackOrForward(steps);
    }

    @Override
    public void goBackOrForward(int steps) {
        Log.v("kc", "goBackOrForward");
        super.goBackOrForward(steps);
    }

    @Override
    public boolean isPrivateBrowsingEnabled() {
        Log.v("kc", "isPrivateBrowsingEnabled");
        return super.isPrivateBrowsingEnabled();
    }

    @Override
    public boolean pageUp(boolean top) {
        Log.v("kc", "pageUp");
        return super.pageUp(top);
    }

    @Override
    public boolean pageDown(boolean bottom) {
        Log.v("kc", "pageDown");
        return super.pageDown(bottom);
    }

    @Override
    public void clearView() {
        Log.v("kc", "clearView");
        super.clearView();
    }

    @Override
    public Picture capturePicture() {
        Log.v("kc", "capturePicture");
        return super.capturePicture();
    }

    @Override
    public float getScale() {
        return super.getScale();
    }

    @Override
    public void setInitialScale(int scaleInPercent) {
        Log.v("kc", "setInitialScale");
        super.setInitialScale(scaleInPercent);
    }

    @Override
    public void invokeZoomPicker() {
        super.invokeZoomPicker();
    }

    @Override
    public HitTestResult getHitTestResult() {
        return super.getHitTestResult();
    }

    @Override
    public void requestFocusNodeHref(Message hrefMsg) {
        super.requestFocusNodeHref(hrefMsg);
    }

    @Override
    public void requestImageRef(Message msg) {
        super.requestImageRef(msg);
    }

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    public String getOriginalUrl() {
        return super.getOriginalUrl();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public Bitmap getFavicon() {
        return super.getFavicon();
    }

    @Override
    public int getProgress() {
        return super.getProgress();
    }

    @Override
    public int getContentHeight() {
        return super.getContentHeight();
    }

    @Override
    public void pauseTimers() {
        super.pauseTimers();
    }

    @Override
    public void resumeTimers() {
        super.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void freeMemory() {
        super.freeMemory();
    }

    @Override
    public void clearCache(boolean includeDiskFiles) {
        super.clearCache(includeDiskFiles);
    }

    @Override
    public void clearFormData() {
        super.clearFormData();
    }

    @Override
    public void clearHistory() {
        super.clearHistory();
    }

    @Override
    public void clearSslPreferences() {
        super.clearSslPreferences();
    }

    @Override
    public WebBackForwardList copyBackForwardList() {
        return super.copyBackForwardList();
    }

    @Override
    public void setFindListener(FindListener listener) {
        super.setFindListener(listener);
    }

    @Override
    public void findNext(boolean forward) {
        super.findNext(forward);
    }

    @Override
    public int findAll(String find) {
        return super.findAll(find);
    }

    @Override
    public void findAllAsync(String find) {
        super.findAllAsync(find);
    }

    @Override
    public boolean showFindDialog(String text, boolean showIme) {
        return super.showFindDialog(text, showIme);
    }

    @Override
    public void clearMatches() {
        super.clearMatches();
    }

    @Override
    public void documentHasImages(Message response) {
        super.documentHasImages(response);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }

    @Override
    public void setDownloadListener(DownloadListener listener) {
        super.setDownloadListener(listener);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        super.setWebChromeClient(client);
    }

    @Override
    public void setPictureListener(PictureListener listener) {
        super.setPictureListener(listener);
    }

    @Override
    public void addJavascriptInterface(Object obj, String interfaceName) {
        super.addJavascriptInterface(obj, interfaceName);
    }

    @Override
    public void removeJavascriptInterface(String name) {
        super.removeJavascriptInterface(name);
    }

    @Override
    public WebSettings getSettings() {
        return super.getSettings();
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
        super.onChildViewAdded(parent, child);
    }

    @Override
    public void onChildViewRemoved(View p, View child) {
        super.onChildViewRemoved(p, child);
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        super.onGlobalFocusChanged(oldFocus, newFocus);
    }

    @Override
    public void setMapTrackballToArrowKeys(boolean setMap) {
        super.setMapTrackballToArrowKeys(setMap);
    }

    @Override
    public void flingScroll(int vx, int vy) {
        super.flingScroll(vx, vy);
    }

    @Override
    public boolean canZoomIn() {
        return super.canZoomIn();
    }

    @Override
    public boolean canZoomOut() {
        return super.canZoomOut();
    }

    @Override
    public boolean zoomIn() {
        return super.zoomIn();
    }

    @Override
    public boolean zoomOut() {
        return super.zoomOut();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
    }

    @Override
    public void setScrollBarStyle(int style) {
        super.setScrollBarStyle(style);
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return super.onHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return super.shouldDelayChildPressedState();
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
    }

    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        return super.performAccessibilityAction(action, arguments);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean performLongClick() {
        return super.performLongClick();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return super.onCreateInputConnection(outAttrs);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        return super.requestChildRectangleOnScreen(child, rect, immediate);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }
}
