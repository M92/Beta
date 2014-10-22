'use strict';

/* 
 *  The MovieLibrary App
 */
var movielibrary = angular.module('MovieLibrary', [
    'ngRoute',
    'MovieCatalogueControllers',
    'MovieCatalogueService'
     // More here
]);


movielibrary.config(['$routeProvider',
    function($routeProvider) {  // Injected object $routeProvider
        $routeProvider.
                when('/movies', {
                    templateUrl: 'partials/movies/movies.html',
                    controller: 'MovieListCtrl'
                    
                }).
                when('/new', {
                    templateUrl: 'partials/movies/movie-new.html',
                    controller: 'MovieNewCtrl'
                }).
                when('/movies/:id', {
                    templateUrl: 'partials/movies/movie-detail.html',
                    controller: 'MovieDetailCtrl'
                }).
                when('/users', {
                    templateUrl: 'partials/users/users.html'
                    //controller: Not used
                }).
                when('/favoritemovies', {
                    templateUrl: 'partials/favoritemovies/favoritemovies.html'
                    //controller: Not used
                }).
                otherwise({
                    redirectTo: '/index.html'
                });

    }]);

