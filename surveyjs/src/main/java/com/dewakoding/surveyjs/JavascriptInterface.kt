package com.dewakoding.surveyjs

import android.content.Context
import android.webkit.JavascriptInterface


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class JavascriptInterface(
    template: String
) {
    companion object {
        const val TAG_HANDLER = "Android"
    }

    private val mtemplate = template

    @JavascriptInterface
    fun getTemplate(): String {
        return mtemplate
    }
}