'use strict';

var app = angular.module('app', [
    'ngRoute'
]);

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/test-page', {
                templateUrl: 'partials/test-page.html'
            }).
            otherwise({
                templateUrl: 'partials/home.html',
                redirectTo: '/'
            });
    }
]);