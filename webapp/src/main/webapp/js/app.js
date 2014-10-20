'use strict';

var app = angular.module('app', [
    'ngRoute'
]);

app.config(['$routeProvider', '$locationProvider',
    function($routeProvider, $locationProvider) {
        $routeProvider.
            when('/test-page', {
                templateUrl: 'partials/test-page.html'
            }).
            otherwise({
                templateUrl: 'partials/home.html',
                redirectTo: '/'
            });
        
        $locationProvider.html5Mode(true);
    }
]);