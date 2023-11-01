// const SURVEY_ID = 1;

const surveyJson = JSON.parse(Android.getForm());
//const theme = Android.getTheme();
const mode = Android.getMode()

const survey = new Survey.Model(surveyJson);
survey.applyTheme(SurveyTheme.ContrastLight);
setData()

function alertResults (sender) {
    const results = JSON.stringify(sender.data);
    Android.getResponse(results)
}

function setData() {


    const data = Android.getData()
    if (data) {
        survey.data = JSON.parse(data)
    }

    if (mode) {
        survey.mode = mode
    }

}

survey.onComplete.add(alertResults);

document.addEventListener("DOMContentLoaded", function() {
    ko.applyBindings({
        model: survey
    });
});