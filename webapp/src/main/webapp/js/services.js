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

services.factory('UserRegistryProxy', ['$http',
    function($http) {
        return {
            addMovieList: function(user, name) {
                var jsonObj = { 'listname': name };
                return $http.post(apiBase + '/users/' + user + '/lists', jsonObj);
            },
            findAllUserLists: function(user){
                return $http.get(apiBase + '/users/' + user + '/lists');
            }
        };
    }
]);

services.factory('ListCatalogueProxy', ['$http',
    function($http) {
        return {
            findAll: function() {
                return $http.get(apiBase + '/lists');
            },
            findList: function(id){
                return $http.get(apiBase + '/lists/' + id);
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
