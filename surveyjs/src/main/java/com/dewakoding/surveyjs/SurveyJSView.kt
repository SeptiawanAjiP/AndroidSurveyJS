package com.dewakoding.surveyjs

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class SurveyJSView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val webView: WebView
    internal var jsi: JavascriptInterface? = null

    private var surveyResponseCallback: SurveyResponseCallback? = null
    private var savedResponse: String? = null

    val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 1
    private var uploadMessage: ValueCallback<*>? = null
    var uploadMessageArray: ValueCallback<Array<Uri>>? = null

    init {
        orientation = VERTICAL
        webView = WebView(context)
    }

    fun setSurveyResponseCallback(callback: SurveyResponseCallback) {
        this.surveyResponseCallback = callback
    }

    fun setTemplate(strTemplate: String) {
        jsi = JavascriptInterface(strTemplate, object : SurveyResponseCallback {
            override fun onSurveyResponseReceived(response: String) {
                savedResponse = response
                surveyResponseCallback?.onSurveyResponseReceived(response)
            }
        })
        render(jsi)
    }

    fun getUploadMessage(): ValueCallback<Array<Uri>>? {
        return uploadMessage as? ValueCallback<Array<Uri>>
    }

    fun setUploadMessage(callback: ValueCallback<Array<Uri>>) {
        uploadMessage = callback
    }

    private fun render(jsi: JavascriptInterface?) {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.settings.allowFileAccess = true
        webView.settings.setDomStorageEnabled(true)
        webView.settings.setAllowContentAccess(true)
        webView.settings.setAllowFileAccess(true)
        webView.clearCache(true)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }
        }

        webView.addJavascriptInterface(jsi!!, JavascriptInterface.TAG_HANDLER)
        webView.webChromeClient = MyWebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.url.toString())
                }
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Toast.makeText(context, description, Toast.LENGTH_SHORT)
            }

            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Toast.makeText(context, errorResponse.toString(), Toast.LENGTH_SHORT)
                }
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Toast.makeText(context, "onReceivedError", Toast.LENGTH_SHORT)
                WebViewClient.ERROR_AUTHENTICATION
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(
                        context,
                        "error code: ${error.errorCode} " + request.url.toString() + " , " + error.description,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                super.onReceivedSslError(view, handler, error)
                Toast.makeText(context, "onReceivedSslError", Toast.LENGTH_SHORT).show()
            }
        }

        val rawResourceId = R.raw.surveyjs
        val inputStream = resources.openRawResource(rawResourceId)
        val htmlData = convertStreamToString(inputStream)
        val baseUrl = "file:///android_res/raw/"
        val dataUri = Uri.parse(baseUrl + rawResourceId)
        webView.loadDataWithBaseURL(baseUrl, htmlData, "text/html", "UTF-8", null)
        addView(webView)
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return stringBuilder.toString()
    }

    internal inner class MyWebChromeClient : WebChromeClient() {
        override fun onPermissionRequest(request: PermissionRequest?) {
            Log.d("Pusat Bantuan Activity", "onPermissionRequest")
        }

        // For 3.0+ Devices (Start)
        protected fun openFileChooser(uploadMsg: ValueCallback<*>, acceptType: String) {
            uploadMessage = uploadMsg
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            (context as Activity).startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
        }

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onShowFileChooser(
            mWebView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            if (uploadMessageArray != null) {
                uploadMessageArray?.onReceiveValue(null)
                uploadMessageArray = null
            }

            uploadMessageArray= filePathCallback

            val intent = fileChooserParams.createIntent()
            try {
                (context as Activity).startActivityForResult(intent, REQUEST_SELECT_FILE)
            } catch (e: Exception) {
                uploadMessageArray = null
                Toast.makeText(context, "Cannot Open File Chooser", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        // For Android 4.1 only
        protected fun openFileChooser(
            uploadMsg: ValueCallback<Uri>,
            acceptType: String,
            capture: String
        ) {
            uploadMessage = uploadMsg
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            (context as Activity).startActivityForResult(
                Intent.createChooser(intent, "File Chooser"),
                FILECHOOSER_RESULTCODE
            )
        }

        protected fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
            uploadMessage = uploadMsg
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            (context as Activity).startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
        }
    }
}
