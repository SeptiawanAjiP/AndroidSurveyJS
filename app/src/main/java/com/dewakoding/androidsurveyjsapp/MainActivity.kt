package com.dewakoding.androidsurveyjsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import com.dewakoding.androidsurveyjsapp.databinding.ActivityMainBinding
import com.dewakoding.surveyjs.SurveyResponseCallback
import com.google.gson.Gson
import org.json.JSONObject
import java.io.InputStreamReader

class MainActivity : AppCompatActivity(), SurveyResponseCallback  {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val template = assets.open("template.json")
            .bufferedReader()
            .use { it.readText() }

        binding.surveyjsView.setTemplate(template)
        binding.surveyjsView.setSurveyResponseCallback(this)

    }

    override fun onSurveyResponseReceived(response: String) {
        Log.d("RESPONSE DATA", response)
    }


}