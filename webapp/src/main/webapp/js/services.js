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

services.factory('UserRegistryProxy', ['$http',
    function($http) {
        return {
            findAllMovieLists: function(user) {
                return $http.get(apiBase + '/users/' + user + '/lists');
            },
            findMovieList: function(user, id) {
                return $http.get(apiBase + '/users/' + user + '/lists/' + id);
            },
            addMovieList: function(user, name) {
                var jsonObj = { 'listname':name };
                return $http.post(apiBase + '/users/' + user + '/lists', jsonObj);
            },
            deleteMovieList: function(user, id) {
                return $http.delete(apiBase + '/users/' + user + '/lists/' + id);
            },
            editMovieList: function(user, id, name, visibility) {
                var jsonObj = { 'listname':name, 'visibility':visibility };
                return $http.put(apiBase + '/users/' + user + '/lists/' + id, jsonObj);
            },
            addMovieToList: function(user, id, movie) {
                return $http.post(apiBase + '/users/' + user + '/lists/' + id, movie);
            },
            removeMovieFromList: function(user, id, movie) {
                return $http.delete(apiBase + '/users/' + user + '/lists/' + id + '/' + movie);
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
