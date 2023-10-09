package com.dewakoding.surveyjs

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
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
class SurveyJSView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val webView: WebView
    internal var jsi: JavascriptInterface? = null

    private var surveyResponseCallback: SurveyResponseCallback? = null
    private var savedResponse: String? = null

    init {
        orientation = VERTICAL
        webView = WebView(context)
    }

    fun setSurveyResponseCallback(callback: SurveyResponseCallback) {
        this.surveyResponseCallback = callback
    }

    fun setTemplate(strTemplate: String) {
        jsi = JavascriptInterface(strTemplate, object : SurveyResponseCallback{
            override fun onSurveyResponseReceived(response: String) {
                savedResponse = response
                surveyResponseCallback?.onSurveyResponseReceived(response)

            }
        })
        render(jsi)
    }
    private fun render(jsi: JavascriptInterface?) {
        val webSettings: WebSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true
        webView.addJavascriptInterface(jsi!!, JavascriptInterface.TAG_HANDLER)
        webView.setWebChromeClient(WebChromeClient())

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
}
