package com.dewakoding.surveyjs

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class JavascriptInterface(
    template: String,
    surveyResponseCallback: SurveyResponseCallback
) {
    companion object {
        const val TAG_HANDLER = "Android"
    }

    private val mtemplate = template
    private val mSurveyResponseCallback = surveyResponseCallback

    @JavascriptInterface
    fun getTemplate(): String {
        return mtemplate
    }
    @JavascriptInterface
    fun getResponse(str: String) {
        mSurveyResponseCallback.onSurveyResponseReceived(str)
    }
}