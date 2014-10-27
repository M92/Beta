'use strict';

var app = angular.module('app', [
    'ngRoute',
    'appControllers',
    'appServices'
]);

app.config(['$routeProvider', '$locationProvider',
    function($routeProvider, $locationProvider) {
        $routeProvider.
            when('/movies/search/:q', {
                templateUrl: 'partials/movie-search.html',
                controller: 'MovieSearchCtrl'
            }).
            when('/movies/search', {
                templateUrl: 'partials/home.html',
                redirectTo: '/'
            }).
            when('/movies/:id', {
                templateUrl: 'partials/movie-detail.html',
                controller: 'MovieDetailCtrl'
            }).
            when('/movies', {
                templateUrl: 'partials/home.html',
                redirectTo: '/'
            }).
            when('/lists/:id', {
                templateUrl: 'partials/movie-list.html',
                controller: 'MovieListCtrl'
            }).
            when('/lists', {
                templateUrl: 'partials/public-lists.html',
                controller: 'PublicListsCtrl'
            }).
            when('/:user/lists', {
                templateUrl: 'partials/my-lists.html',
                controller: 'UserListsCtrl'
            }).
            otherwise({
                templateUrl: 'partials/home.html',
                redirectTo: '/'
            });
        
        $locationProvider.html5Mode(true);
    }
]);