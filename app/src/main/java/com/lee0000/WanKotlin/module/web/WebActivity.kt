package com.lee0000.WanKotlin.module.web

import android.graphics.Bitmap
import android.os.Build
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.wan_activity_web.*

/**
author: Lee
date:   2022/4/13
 */
class WebActivity: BaseActivity() {

    private lateinit var webView: WebView
    private lateinit var webSetting: WebSettings

    override fun getLayoutResId(): Int {
        return R.layout.wan_activity_web
    }

    override fun initView() {
        val paramBundle = intent.getBundleExtra(IntentUtil.PARAM_BUNDLE)
        val url = paramBundle?.getString("url")

        webView = WebView(this.applicationContext)
        webViewFl.addView(webView)

        webSetting = webView.settings
        // 支持屏幕缩放
        webSetting.setSupportZoom(true)
        // 设置内置的缩放控件。若为false，则该WebView不可缩放
        webSetting.builtInZoomControls = true
        // 不显示webview缩放按钮
        webSetting.displayZoomControls = false
        // 设置自适应屏幕宽度
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true)
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        // 设置缓存模式
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
        // 允许android调用javascript
        webSetting.domStorageEnabled = true
        // 设置WebView对象的编码格式为UTF_8
        webSetting.defaultTextEncodingName = "utf-8"
        // 解决图片不显示
        webSetting.blockNetworkImage = false
        // 支持自动加载图片
        webSetting.loadsImagesAutomatically = true

        webView.webViewClient = WanWebViewClient()
        webView.webChromeClient = WanWebChromeClient()

        webView.loadUrl(url)
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()

        webView?.onResume()
    }

    override fun onPause() {
        super.onPause()

        webView?.onPause()
    }

    override fun onDestroy() {

        webView.stopLoading()
        webView.onPause()
        webView.clearCache(true)
        webView.clearHistory()
        webView.removeAllViews()
        webView.destroyDrawingCache()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.removeAllViews()
            webViewFl.removeView(webView)
            webView.destroy()
        } else {
            webView.removeAllViews()
            webViewFl.removeView(webView)
            webView.destroy()
        }

        super.onDestroy()
    }

    private class WanWebViewClient: WebViewClient() {

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
            return super.shouldOverrideUrlLoading(p0, p1)
        }

        override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
            super.onPageStarted(p0, p1, p2)
        }

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(p0, p1)
        }

        override fun onPageFinished(p0: WebView?, p1: String?) {
            super.onPageFinished(p0, p1)
        }
    }

    private class WanWebChromeClient: WebChromeClient() {

        override fun onProgressChanged(p0: WebView?, p1: Int) {

        }
    }
}