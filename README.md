# AndroidSurveyJS
![image](https://raw.githubusercontent.com/SeptiawanAjiP/AndroidSurveyJS/master/demo.jpeg)

AndroidSurveyJS is an Android library that allows you to run SurveyJS surveys offline on Android devices. It provides a convenient way to integrate SurveyJS with your Android applications, making it easy to create and manage surveys.

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
