// const SURVEY_ID = 1;

console.log(JSON.parse(Android.getTemplate()))
const surveyJson = JSON.parse(Android.getTemplate());

const survey = new Survey.Model(surveyJson);

function alertResults (sender) {
    const results = JSON.stringify(sender.data);

    Android.getResponse(results)
}

survey.onComplete.add(alertResults);

document.addEventListener("DOMContentLoaded", function() {
    ko.applyBindings({
        model: survey
    });
});

// function saveSurveyResults(url, json) {
//     fetch(url, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json;charset=UTF-8'
//         },
//         body: JSON.stringify(json)
//     })
//     .then(response => {
//         if (response.ok) {
//             // Handle success
//         } else {
//             // Handle error
//         }
//     })
//     .catch(error => {
//         // Handle error
//     });
// }