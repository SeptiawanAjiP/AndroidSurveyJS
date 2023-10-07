// const SURVEY_ID = 1;

const surveyJson = {
                    "title": "Product/Market Fit Survey Template",
                    "pages": [
                     {
                      "name": "page1",
                      "elements": [
                       {
                        "type": "radiogroup",
                        "name": "customer_role",
                        "title": "What best describes your role?",
                        "choices": [
                         "Engineering Lead",
                         "Project Manager",
                         "Software Developer",
                         "Designer",
                         "Product Manager",
                         "CEO / Founder",
                         "Customer Support"
                        ],
                        "showOtherItem": true,
                        "otherText": "Other",
                        "colCount": 3
                       },
                       {
                        "type": "radiogroup",
                        "name": "start_using",
                        "title": "How did you start using the product?",
                        "choices": [
                         {
                          "value": "created",
                          "text": "I created my account"
                         },
                         {
                          "value": "invited",
                          "text": "I was invited to an account"
                         }
                        ]
                       },
                       {
                        "type": "radiogroup",
                        "name": "product_discovering",
                        "title": "How did you first discover the product? ",
                        "choices": [
                         "Friend or colleague",
                         "Search engine",
                         "Facebook",
                         "Twitter",
                         "Blog"
                        ],
                        "showOtherItem": true,
                        "otherText": "Other",
                        "colCount": 3
                       },
                       {
                        "type": "radiogroup",
                        "name": "paid_customer",
                        "title": "Do you currently pay for the product? ",
                        "isRequired": true,
                        "choices": [
                         "Yes",
                         "No"
                        ]
                       }
                      ]
                     },
                     {
                      "name": "page2",
                      "elements": [
                       {
                        "type": "radiogroup",
                        "name": "product_fit",
                        "title": "How would you feel if you could no longer use the product?",
                        "isRequired": true,
                        "choices": [
                         {
                          "value": "3",
                          "text": "Very disappointed"
                         },
                         {
                          "value": "2",
                          "text": "Somewhat disappointed"
                         },
                         {
                          "value": "1",
                          "text": "Not disappointed"
                         }
                        ]
                       },
                       {
                        "type": "comment",
                        "name": "product_fit_comment",
                        "visible": false,
                        "visibleIf": "{product_fit} notempty",
                        "title": "Please help us understand why you selected the answer above"
                       }
                      ]
                     },
                     {
                      "name": "page3",
                      "elements": [
                       {
                        "type": "radiogroup",
                        "name": "product_alternative",
                        "title": "What would you use as an alternative if [the product] were no\nlonger available?",
                        "choices": [
                         "Alternative 1",
                         "Alternative 2",
                         "Alternative 3",
                         "Alternative 4",
                         "Alternative 5",
                         "Alternative 6"
                        ],
                        "showOtherItem": true,
                        "otherText": "Other (please name)",
                        "colCount": 3
                       },
                       {
                        "type": "radiogroup",
                        "name": "product_benefit",
                        "title": "What is the primary benefit that you have received from the\nproduct?",
                        "choices": [
                         "Benefit 1",
                         "Benefit 2",
                         "Benefit 3",
                         "Benefit 4",
                         "Benefit 5",
                         "Benefit 6"
                        ],
                        "showOtherItem": true,
                        "colCount": 3
                       },
                       {
                        "type": "radiogroup",
                        "name": "product_recommend",
                        "title": "Have you recommended the product to anyone?",
                        "choices": [
                         "Yes",
                         "No"
                        ]
                       }
                      ]
                     },
                     {
                      "name": "page4",
                      "elements": [
                       {
                        "type": "rating",
                        "name": "nps_score",
                        "title": "How likely are you to recommend the product to a friend or\ncolleague? ",
                        "isRequired": true,
                        "rateCount": 11,
                        "rateMin": 0,
                        "rateMax": 10,
                        "minRateDescription": "Most unlikely",
                        "maxRateDescription": "Most likely"
                       },
                       {
                        "type": "radiogroup",
                        "name": "favorite_functionality",
                        "title": "What's your favorite functionality / add-on for the product?",
                        "choices": [
                         "Feature 1",
                         "Feature 2",
                         "Feature 3",
                         "Feature 4",
                         "Feature 5",
                         "Feature 6"
                        ],
                        "showOtherItem": true,
                        "colCount": 3
                       },
                       {
                        "type": "comment",
                        "name": "product_improvement",
                        "title": "How could the product be improved to better meet your\nneeds?"
                       }
                      ]
                     },
                     {
                      "name": "page5",
                      "elements": [
                       {
                        "type": "multipletext",
                        "name": "contact_customer",
                        "title": "Want us to follow-up? Leave your name and email here:",
                        "items": [
                         {
                          "name": "Name"
                         },
                         {
                          "name": "E-mail",
                          "inputType": "email",
                          "validators": [
                           {
                            "type": "email"
                           }
                          ]
                         }
                        ]
                       }
                      ]
                     }
                    ]
                   };

const survey = new Survey.Model(surveyJson);

function alertResults (sender) {
    const results = JSON.stringify(sender.data);
    alert(results);
    // saveSurveyResults(
    //     "https://your-web-service.com/" + SURVEY_ID,
    //     sender.data
    // )
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