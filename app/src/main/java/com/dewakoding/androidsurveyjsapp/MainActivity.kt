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
import com.dewakoding.surveyjs.SurveyResponseCallback
import com.dewakoding.surveyjs.utils.Const
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), SurveyResponseCallback  {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    private var mUploadMessage: ValueCallback<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val template = assets.open("template-image.json")
            .bufferedReader()
            .use { it.readText() }

//        binding.surveyjsView.requestPermission()
        binding.surveyjsView.setTemplate(template)
        binding.surveyjsView.setSurveyResponseCallback(this)
        uploadMessage = binding.surveyjsView.getUploadMessage()

    }

    override fun onSurveyResponseReceived(response: String) {
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            response,
            Snackbar.LENGTH_LONG
        ).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("AUFAR", ""+requestCode)

            if (requestCode === Const.REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return
                var results: Array<Uri>? =
                    WebChromeClient.FileChooserParams.parseResult(resultCode, data)

                Log.d("AUFAR", ""+results.toString())
                uploadMessage?.onReceiveValue(results)
                uploadMessage = null
            }
        } else if (requestCode === Const.FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return
            val result = if (intent == null || resultCode !== RESULT_OK) null else intent.data
            mUploadMessage?.onReceiveValue(null)
            mUploadMessage = null
        } else {
            Toast.makeText(applicationContext, "Failed to Upload Image", Toast.LENGTH_LONG).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Const.PERMISSION_CODE-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // Izin diberikan, lanjutkan dengan logika Anda di sini
                } else {
                    // Izin ditolak, Anda dapat memberikan pemberitahuan kepada pengguna atau menangani kasus ini sesuai kebutuhan
                }
            }

        }

    }
}