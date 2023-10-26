package com.dewakoding.androidsurveyjsapp

import android.os.Bundle
import android.view.View
import com.dewakoding.surveyjs.SurveyJSActivity
import com.dewakoding.surveyjs.SurveyResponseCallback
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
        setForm(template, data, "display", this)

    }

    override fun onSurveyResponseReceived(response: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            response,
            Snackbar.LENGTH_LONG
        ).show()
    }
}