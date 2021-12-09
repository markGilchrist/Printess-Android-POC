package com.gameforeverything.printess_android_poc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gameforeverything.printess_android_poc.databinding.ActivityFullscreenBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding

    @SuppressLint("ClickableViewAccessibility", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        WebView.setWebContentsDebuggingEnabled(true)
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.setSupportZoom(true)
            settings.defaultTextEncodingName = "utf-8"
            addJavascriptInterface(WebAppInterface(this@FullscreenActivity), "ui")
            loadUrl(printessUrl)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    view?.loadUrl(
                        "javascript:startPrintess('eyJhbGciOiJSUzI1NiIsImtpZCI6InByaW50ZXNzLXNhYXMtYWxwaGEiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiJSV0g5NzdoYjFqUzdiR3M4aXNPdHNvQnZySnUyIiwianRpIjoiMzJwTm9nTzUwQkxBRXh4OXVYRzlsRzhjcXJ3ajJQT0ciLCJyb2xlIjoic2hvcCIsIm5iZiI6MTYxODk4NjE4MCwiZXhwIjoxOTM0MzQ2MTgwLCJpYXQiOjE2MTg5ODYxODAsImlzcyI6IlByaW50ZXNzIEdtYkggJiBDby5LRyIsImF1ZCI6InByaW50ZXNzLXNhYXMifQ.XjVlVPzLwIYFwNNrxfWhIgCprjUHAJXsD7nz_qI9WSWhIu-DY9fwSKVQNB_QKiuRNkIzCxzgfiZSe3d3k8Rd88_ixPjw7e3N0O1gyciLzwQQ0nWoJiwXditT1CZp9LhwxR7SGUe6hJK_gLBh_boeeN0jVlwV45EFIHSU7AzeeKC7_1WJAcb0-qpMU6TWAsamj1MvzDTAbNePMPJ6sqULneIUpjME42V3cfCu_x0FD8QMZIkVWpnjZqatVPstmfzsoaTpFCYqPcnFLEEmbfL0KFdir0ieodC69Tl4T4183cqzAa8qrF6kYeRK31OjBUh8rLdgDg4mrw7Yyl1_ndRqe366Qrfym_xM0C9Lj2tKB7bduIftlfkpdRk30M2TqmDHaM1Dq8He8X2PHvd5uy9rGj-1rWugWbEhaXiyyoQJWzv1apOXVNz2yecc4QHYQyNnEVuAadLWkX5YaNZhBU7CSpWffSwzPeEfLEHh57j74J11J_BT89KzKtjo90uhPk4_MAE0qj6QbYUL_16vmajiAQZJicPifTI4ByoMdEdJ7pvHHvfUdBYQtHHfjZd7d60k7HaZFbRP5RWs7H3ZfQF5sazrrHesEWngi_WjgrXqNRdfVxhbs3tcSLfAfm_loJJHxKILGL5JiV9WJCsksKkMatLoCmvCdHHS3cn2NYIx3UQ','Card', 'draft', 'someBasketId', 'someShopUserId', 'backButtonCallback', 'addToBasketCallback');"
                    )
                }
            }
        }
    }


    companion object {
        private const val printessUrl =
            "https://printess-editor.s3.eu-central-1.amazonaws.com/v/nightly/printess-editor/buyer-test-ios.html"
    }
}

class WebAppInterface(private val mContext: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun backButtonCallback(token: String) {
        Toast.makeText(mContext, "Save Token: $token)", Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun addToBasketCallback(map: Map<String, String>) {
        Toast.makeText(
            mContext,
            "Save Token: (${map["token"]}), thumbnail url: (${map["imageUrl"]}))",
            Toast.LENGTH_SHORT
        ).show()
    }
}