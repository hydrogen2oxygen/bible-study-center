/*
 * Copyright by Pietro Lusso 2019
 */

var token = $('#_csrf').attr('content');
var header = $('#_csrf_header').attr('content');


var timelineRepository = {};

timelineRepository.create = function (name) {
  var timeline = {name:name,timelineObjects:[]};
  return timeline;
};

timelineRepository.save = function (timeline, successCallback) {
    $.ajax({
        type: 'POST',
        url: 'api/timelines',
        data: JSON.stringify(timeline),
        success: successCallback,
        contentType: "application/json",
        dataType: 'json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });
}

// ---- We test everything ----

var test = {};
var testResult = {};

test.testPersistence = function () {

    var timeline = timelineRepository.create('test');
    timelineRepository.save(timeline, function (data) {

        testResult.persistenceTest = "Success!";
        testResult.persistenceTestData = data;
        test.callbackAfterDone();
    });
};

test.suite = function (callbackAfterDone) {
    test.callbackAfterDone = callbackAfterDone;
    test.testPersistence();

};