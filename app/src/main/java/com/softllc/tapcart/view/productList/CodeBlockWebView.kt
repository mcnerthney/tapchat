package com.softllc.tapcart.view.productList

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import timber.log.Timber

class CodeBlockWebView : WebView {

    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) { init() }


    private fun init() {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webViewClient = CodeBlockWebViewClient()
    }


}

class CodeBlockWebViewClient(): WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Timber.d("shouldOverrideUrlLoading ${view?.url} ${request?.url}")
        return true
    }
}

