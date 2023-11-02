package com.dewakoding.surveyjs


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
interface SurveyResponseCallback {
    fun onSurveyComplete(response: String)

    fun onSurveyUnComplete(response: String)
}