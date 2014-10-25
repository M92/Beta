'use strict';

var services = angular.module('appServices', []);

services.factory('RottenTomatoesProxy', ['$http',
    function($http) {
        var uriBase = 'http://api.rottentomatoes.com/api/public/v1.0';
        var callback = '?callback=JSON_CALLBACK';
        var apiKey = '&apikey=jh8ajhuq7cwq7y6k4d2ymabs';
        return {
            movieSearch: function(title, results, page) {
                return $http.jsonp(uriBase + '/movies.json' + callback + '&q=' +
                        title + '&page_limit=' + results + '&page=' + page + apiKey);
            },
            movieDetail: function(id) {
                return $http.jsonp(uriBase + '/movies/' + id + '.json' + callback + apiKey);
            }
        };
    }
]);
