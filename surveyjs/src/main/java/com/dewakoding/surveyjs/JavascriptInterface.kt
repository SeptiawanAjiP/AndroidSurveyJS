package com.dewakoding.surveyjs

import android.webkit.JavascriptInterface


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class JavascriptInterface(
    form: String,
    data: String?,
    mode: String,
    surveyResponseCallback: SurveyResponseCallback
) {
    companion object {
        const val TAG_HANDLER = "Android"
    }

    private val mForm = form
    private val mData = data
    private val mMode = mode
    private val mSurveyResponseCallback = surveyResponseCallback

    @JavascriptInterface
    fun getForm(): String {
        return mForm
    }

    @JavascriptInterface
    fun getData(): String? {
        return mData
    }

    @JavascriptInterface
    fun getMode(): String {
        return mMode
    }


    @JavascriptInterface
    fun getResponse(str: String) {
        mSurveyResponseCallback.onSurveyResponseReceived(str)
    }
}