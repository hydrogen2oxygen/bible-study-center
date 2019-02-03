/*
 * Copyright by Pietro Lusso 2019
 */

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
        dataType: 'json'
    });
}

var test = {};
test.testPersistence = function () {

    var timeline = timelineRepository.create('test');
    timelineRepository.save(timeline, function (data) {
        console.log(data);
    });
};