'use strict';

var services = angular.module('appServices', []);

var apiBase = 'http://localhost:8080/webapp/api';

services.factory('AuthService', ['$http',
    function($http) {
        return {
            getSession: function() {
                return $http.get(apiBase + '/session');
            }
        };
    }
]);

services.factory('UserListsProxy', ['$http',
    function($http) {
        return {
            addMovieList: function(user, name) {
                var jsonObj = { 'listname': name };
                return $http.post(apiBase + '/' + user + '/lists', jsonObj);
            }
        };
    }
]);

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
