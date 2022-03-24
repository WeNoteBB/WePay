package com.yctapp.base.frame.pay.interceptor.icbc

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.wenote.icbcsz.BuildConfig
import com.wenote.icbcsz.R
import kotlinx.android.synthetic.main.activity_browser.*


class ICBCSBFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.WhiteBlackTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        var data = intent.getStringExtra("data")

        iv_back.setOnClickListener {
            finish()
        }

        tv_finish.setOnClickListener {
            finish()
        }

        tv_finish.setTextColor(Color.BLACK)
        webview_title.setTextColor(Color.BLACK)
        webview_title.text = intent.getStringExtra("title")
        webview_toolbar.setBackgroundColor(Color.WHITE)

        val webSettings = browser_web!!.settings
        webSettings.run {
            javaScriptEnabled = true
            defaultTextEncodingName = "UTF-8"
            allowFileAccess = true
            userAgentString = webSettings.userAgentString + ";yctAndroidWebview"
            allowContentAccess = true
            useWideViewPort = true
            loadWithOverviewMode = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            databaseEnabled = true
            setGeolocationEnabled(true)
            domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        browser_web.webViewClient = WebViewClient()

        if (data != null) {
            browser_web.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
        }
    }

    companion object {
        fun startForResult(activity: Activity, Url: String) {
            val intent = Intent(activity, ICBCSBFormActivity::class.java)
            intent.putExtra("data", Url)
            activity.startActivity(intent)
        }
    }
}