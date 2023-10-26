// const SURVEY_ID = 1;

const surveyJson = JSON.parse(Android.getForm());
const data = JSON.parse(Android.getData())
const mode = Android.getMode()

const survey = new Survey.Model(surveyJson);
setData()

function alertResults (sender) {
    const results = JSON.stringify(sender.data);
    Android.getResponse(results)
}

function setData() {
    if (data) {
        survey.data = data
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