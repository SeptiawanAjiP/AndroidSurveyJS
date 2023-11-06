package com.dewakoding.androidsurveyjsapp

import android.os.Bundle
import android.view.View
import com.dewakoding.surveyjs.SurveyJSActivity
import com.dewakoding.surveyjs.SurveyResponseCallback
import com.dewakoding.surveyjs.utils.SurveyJSConst
import com.google.android.material.snackbar.Snackbar

class MainActivity : SurveyJSActivity(), SurveyResponseCallback  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val template = assets.open("template.json")
            .bufferedReader()
            .use { it.readText() }

        val data = assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }


        setForm(template, data, SurveyJSConst.MODE_EDIT, SurveyJSConst.THEME_LAYERED,this)

    }

    override fun onSurveyComplete(response: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            response,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onSurveyUnComplete(response: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            response,
            Snackbar.LENGTH_LONG
        ).show()
    }
}