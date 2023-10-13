# AndroidSurveyJS
![image](https://raw.githubusercontent.com/SeptiawanAjiP/AndroidSurveyJS/master/demo.jpeg)

AndroidSurveyJS is a versatile library for Android that enables you to display generic forms. and run SurveyJS surveys offline on Android devices. This library provides a convenient way to integrate SurveyJS with your Android applications, making it easy to create and manage surveys, as well as customize forms and questionnaires to meet your specific needs.

**[SurveyJS](https://surveyjs.io/)** is a powerful and versatile JavaScript library for building online surveys and forms. With AndroidSurveyJS, you can seamlessly bring SurveyJS functionality to Android applications, even when offline.

## Installation

Use Gradle. Add it in your settings.gradle at the end of repositories:

```bash
dependencyResolutionManagement {
    repositories {
        ....
        maven { url 'https://jitpack.io' }
    }
}
```
then, add the dependecy in your build.gradle file (Module)
```bash
dependencies {
    implementation 'com.github.SeptiawanAjiP:AndroidSurveyJS:Tag'
}
```
replace Tag with the latest version.
## Usage
### XML Layout
```bash
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.dewakoding.surveyjs.SurveyJSView
        android:id="@+id/surveyjs_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
### Activity
```bash
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
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            response,
            Snackbar.LENGTH_LONG
        ).show()

    }
}
```