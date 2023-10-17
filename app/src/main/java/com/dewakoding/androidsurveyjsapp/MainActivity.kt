package com.dewakoding.androidsurveyjsapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.dewakoding.androidsurveyjsapp.databinding.ActivityMainBinding
import com.dewakoding.surveyjs.SurveyJSActivity
import com.dewakoding.surveyjs.SurveyResponseCallback
import com.dewakoding.surveyjs.utils.Const
import com.google.android.material.snackbar.Snackbar

class MainActivity : SurveyJSActivity(), SurveyResponseCallback  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val template = assets.open("template-image.json")
            .bufferedReader()
            .use { it.readText() }
        setTemplate(template, this)

    }

    override fun onSurveyResponseReceived(response: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            response,
            Snackbar.LENGTH_LONG
        ).show()
    }
}