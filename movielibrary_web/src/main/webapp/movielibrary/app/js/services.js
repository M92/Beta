'use strict';

/* Services */

var movieCatalogueService = angular.module('MovieCatalogueService', []);
var userRegistryService = angular.module('UserRegistryService', []);

// Representing the remote RESTful MovieCatalogue
movieCatalogueService.factory('MovieCatalogueProxy', ['$http',
        function($http) {
            var url = 'http://localhost:8080/movielibrary_web/webresources/movies';

            return {
                findAll: function() {
                    return $http.get(url);
                },
                findRange: function(first, max) {
                    return $http.get(url + "/range?fst=" + first + "&max=" + max);
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
                    return $http.get(url + "/max");
                }
            };
        }
    ]
);
/*
userRegistryService.factory('UserRegisterProxy', ['$http',
        function($http) {
            var listUrl = 'http://localhost:8080/movielibrary_web/webresources/users';

            return {
                
                findAll: function() {
                    return $http.get(listUrl);
                },
                findRange: function(first, max) {
                    return $http.get(listUrl + "/range?fst=" + first + "&max=" + max);
                },
                find: function(id) {
                    return $http.get(listUrl + "/" + id);
                },
                update: function(id, movie) {
                    return $http.put(listUrl + "/" + id, movie);
                },
                create: function(movie) {
                    return $http.post(listUrl, movie);
                },
                deleteList: function(id) {
                    return $http.delete(listUrl + "/" + id);
                },
                deleteUser: function(id) {
                    return $http.delete(userUrl + "/" + id);
                },
                count: function() {
                    return $http.get(listUrl + "/max");
                }
            };
        }
    ]
);

*/