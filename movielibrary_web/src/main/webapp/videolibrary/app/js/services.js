'use strict';

/* Services */

var movieCatalogueService = angular.module('MovieCatalogueService', []);

// Representing the remote RESTful MovieCatalogue
movieCatalogueService.factory('MovieCatalogueProxy', ['$http',
        function($http) {
            var url = 'http://localhost:8080/movielibrary_web/webresources/movies';

            return {
                findAll: function() {
                    return $http.get(url);
                },
                findRange: function(first, count) {
                    return $http.get(url + "/range?fst=" + first + "&count=" + count);
                },
                find: function(id) {
                    return $http.get(url + "/" + id);
                },
                update: function(id, movie) {
                    return $http.put(url + "/" + id, movie);
                },
                create: function(movie) {
                    return $http.post(url, movie);
                },
                delete: function(id) {
                    return $http.delete(url + "/" + id);
                },
                count: function() {
                    return $http.get(url + "/count");
                }
            };
        }
    ]
);

