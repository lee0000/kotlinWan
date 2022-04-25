package com.lee0000.WanKotlin.module.web

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.URL
import com.lee0000.WanKotlin.module.base.BaseActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lxj.statelayout.StateLayout
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.wan_activity_web.*

/**
author: Lee
date:   2022/4/13
 */
class WebActivity: BaseActivity() {

    private lateinit var stateLayout: StateLayout
    private lateinit var webView: WebView
    private lateinit var webSetting: WebSettings

    private var url = ""

    override fun getLayoutResId(): Int {
        return R.layout.wan_activity_web
    }

    override fun initView(savedInstanceState: Bundle?) {

        // 判断是否从缓存中恢复数据
        url = if (savedInstanceState != null){
            savedInstanceState.getString(URL).toString()
        }else{
            val paramBundle = intent.getBundleExtra(IntentUtil.PARAM_BUNDLE)
            paramBundle?.getString(URL).toString()
        }

        stateLayout = StateLayout(this).wrap(webViewFl)

        iv_back.setOnClickListener {
            finish()
        }

        webView = WebView(this.applicationContext)
        webViewFl.addView(webView)

        webViewSettings()

        webView.webViewClient = WanWebViewClient(stateLayout)
        webView.webChromeClient = WanWebChromeClient()

        webView.loadUrl(url)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL, url)
    }

    override fun initData() {}

    private fun webViewSettings(){
        webSetting = webView.settings
        // 支持javaScript
        webSetting.javaScriptCanOpenWindowsAutomatically = true
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

    private class WanWebViewClient(private val stateLayout: StateLayout) : WebViewClient() {

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
            return super.shouldOverrideUrlLoading(p0, p1)
        }

        override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
            super.onPageStarted(p0, p1, p2)

            stateLayout.showLoading()
        }

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(p0, p1)
        }

        override fun onPageFinished(p0: WebView?, p1: String?) {
            super.onPageFinished(p0, p1)

            stateLayout.showContent()
        }
    }

    private class WanWebChromeClient: WebChromeClient() {

        override fun onProgressChanged(p0: WebView?, p1: Int) {

        }
    }
}