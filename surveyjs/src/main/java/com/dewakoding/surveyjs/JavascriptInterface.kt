package com.dewakoding.surveyjs

import android.content.Context
import android.webkit.JavascriptInterface


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class JavascriptInterface() {
    companion object {
        const val TAG_HANDLER = "Android"
    }

    @JavascriptInterface
    fun getTemplate(): String {
        return ""
    }
}