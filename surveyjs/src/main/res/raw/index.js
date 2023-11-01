// const SURVEY_ID = 1;

const surveyJson = JSON.parse(Android.getForm())
const mode = Android.getMode()

const survey = new Survey.Model(surveyJson);

setTheme()
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

function setTheme() {
    const theme = Android.getTheme()
    switch(theme) {
      case "1":
        survey.applyTheme(SurveyTheme.SharpLight);
        break;
      case "2":
        survey.applyTheme(SurveyTheme.BorderlessLight);
        break;
      case "3":
        survey.applyTheme(SurveyTheme.FlatLight);
        break;
      case "4":
          survey.applyTheme(SurveyTheme.PlainLight);
          break;
      case "5":
          survey.applyTheme(SurveyTheme.DoubleBorderLight);
          break;
      case "6":
          survey.applyTheme(SurveyTheme.LayeredLight);
          break;
       case "7":
           survey.applyTheme(SurveyTheme.SolidLight);
           break;
       case "8":
           survey.applyTheme(SurveyTheme.ThreeDimensionalLight);
           break;
       case "9":
           survey.applyTheme(SurveyTheme.ContrastLight);
           break;
      default:

    }

}

survey.onComplete.add(alertResults);

document.addEventListener("DOMContentLoaded", function() {
    ko.applyBindings({
        model: survey
    });
});