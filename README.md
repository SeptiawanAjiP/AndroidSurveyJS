# AndroidSurveyJS
![image](https://raw.githubusercontent.com/SeptiawanAjiP/AndroidSurveyJS/master/demo.jpeg)

AndroidSurveyJS is a versatile library for Android that enables you to display generic forms and run SurveyJS surveys offline on Android devices. This library provides a convenient way to integrate SurveyJS with your Android applications, making it easy to create and manage surveys, as well as customize forms and questionnaires to meet your specific needs.

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
```bash
class MainActivity : SurveyJSActivity(), SurveyResponseCallback  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val template = assets.open("template.json")
            .bufferedReader()
            .use { it.readText() }

      // If data is empty or being opened for the first time, you can set it as null
        val data = assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }

        // Choose the mode: SurveyJSConst.MODE_DISPLAY for display or read-only, SurveyJSConst.MODE_EDIT to enable editing
        // You can change theme of page view
        setForm(template, data, SurveyJSConst.MODE_DISPLAY, SurveyJSConst.THEME_LAYERED, this)

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
```

## To do