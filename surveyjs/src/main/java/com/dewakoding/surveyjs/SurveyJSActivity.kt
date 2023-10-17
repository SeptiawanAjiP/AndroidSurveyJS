package com.dewakoding.surveyjs

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dewakoding.surveyjs.databinding.ActivitySurveyJsBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
open class SurveyJSActivity: AppCompatActivity() {

    private val binding by lazy { ActivitySurveyJsBinding.inflate(layoutInflater) }
    internal var jsi: JavascriptInterface? = null
    private var savedResponse: String? = null
    private val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 1
    var uploadMessageArray: ValueCallback<Array<Uri>>? = null
    var link: String? = null
    private var uploadMessage: ValueCallback<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO,
                ),
                1
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
                1
            )
        }

    }

    open fun setTemplate(strTemplate: String, surveyResponseCallback: SurveyResponseCallback) {
        jsi = JavascriptInterface(strTemplate, object : SurveyResponseCallback {
            override fun onSurveyResponseReceived(response: String) {
                savedResponse = response
                surveyResponseCallback?.onSurveyResponseReceived(response)
            }
        })
        loadContent(jsi)
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun loadContent(jsi: JavascriptInterface?) {
        binding.webViewComponent.settings.javaScriptEnabled = true
        binding.webViewComponent.settings.allowFileAccess = true
        binding.webViewComponent.settings.setDomStorageEnabled(true);
        binding.webViewComponent.settings.setAllowContentAccess(true);
        binding.webViewComponent.settings.setAllowFileAccess(true);
        binding.webViewComponent.clearCache(true)
        binding.webViewComponent.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }
        }
        binding.webViewComponent.addJavascriptInterface(jsi!!, JavascriptInterface.TAG_HANDLER)
        binding.webViewComponent.webChromeClient = MyWebChromeClient()

        binding.webViewComponent.webViewClient = object : WebViewClient() {
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
                Toast.makeText(applicationContext, description, Toast.LENGTH_SHORT)
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
                    Toast.makeText(applicationContext, errorResponse.toString(), Toast.LENGTH_SHORT)
                }
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Toast.makeText(applicationContext, "onReceivedError", Toast.LENGTH_SHORT)
                WebViewClient.ERROR_AUTHENTICATION
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(
                        applicationContext,
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
                Toast.makeText(applicationContext, "onReceivedSslError", Toast.LENGTH_SHORT).show()
            }

        }

        val rawResourceId = R.raw.surveyjs
        val inputStream = resources.openRawResource(rawResourceId)
        val htmlData = convertStreamToString(inputStream)
        val baseUrl = "file:///android_res/raw/"
        val dataUri = Uri.parse(baseUrl + rawResourceId)
        binding.webViewComponent.loadDataWithBaseURL(baseUrl, htmlData, "text/html", "UTF-8", null)
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

        }
        protected fun openFileChooser(uploadMsg: ValueCallback<*>, acceptType: String) {
            uploadMessage = uploadMsg
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onShowFileChooser(
            mWebView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            if (uploadMessageArray != null) {
                uploadMessageArray!!.onReceiveValue(null)
                uploadMessageArray = null
            }

            uploadMessageArray = filePathCallback

            val intent = fileChooserParams.createIntent()
            try {
                startActivityForResult(intent, REQUEST_SELECT_FILE)
            } catch (e: Exception) {
                uploadMessageArray = null
                Toast.makeText(applicationContext, "Cannot Open File Chooser", Toast.LENGTH_SHORT)
                    .show()
                return false
            }

            return true
        }

        protected fun openFileChooser(
            uploadMsg: ValueCallback<Uri>,
            acceptType: String,
            capture: String
        ) {
            uploadMessage = uploadMsg
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(
                Intent.createChooser(intent, "File Chooser"),
                FILECHOOSER_RESULTCODE
            )
        }

        protected fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
            uploadMessage = uploadMsg
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode === REQUEST_SELECT_FILE) {
                if (uploadMessageArray == null)
                    return
                print("result code = " + resultCode)
                var results: Array<Uri>? =
                    WebChromeClient.FileChooserParams.parseResult(resultCode, data)
                uploadMessageArray?.onReceiveValue(results)
                uploadMessageArray = null
            }
        } else if (requestCode === FILECHOOSER_RESULTCODE) {
            if (null == uploadMessage)
                return
            val result = if (intent == null || resultCode !== RESULT_OK) null else intent.data
            uploadMessage?.onReceiveValue(null)
            uploadMessage = null
        } else {
            Toast.makeText(applicationContext, "Failed to Upload Image", Toast.LENGTH_LONG).show()
        }
    }
}