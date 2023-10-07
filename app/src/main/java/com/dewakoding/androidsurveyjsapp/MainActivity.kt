package com.dewakoding.androidsurveyjsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dewakoding.androidsurveyjsapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONObject
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val template = assets.open("template.json")
            .bufferedReader()
            .use { it.readText() }

        Log.d("AUFAR", template.toString())

        binding.surveyjsView.setTemplate(template)

    }
}