'use strict';

var controllers = angular.module('appControllers', []);

controllers.controller('NavigationCtrl', ['$scope',
    '$location',
    function($scope, $location) {
        $scope.navigate = function(url) {
            $location.path(url);
        };
    }
]);

controllers.controller('AuthCtrl', ['$scope',
    'AuthService',
    function($scope, AuthService) {
        AuthService.getSession().
            success(function(data, status) {
                if (status === 200) {
                    $scope.currentUser = data.username;
                    $scope.isLoggedIn = true;
                } else {
                    $scope.currentUser = '';
                    $scope.isLoggedIn = false;
                }
            });
    }
]);

controllers.controller('UserListsCtrl', ['$scope',
    '$location', '$routeParams', 'UserListsProxy',
    function($scope, $location, $routeParams, UserListsProxy) {
        $scope.addMovieList = function() {
            UserListsProxy.addMovieList($routeParams.user, $scope.name).
                success(function(data) {
                    $location.path($routeParams.user + '/lists/' + data.id);
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
    }
]);

controllers.controller('PublicListsCtrl', ['$scope',
    'PublicListsProxy',
    function($scope, PublicListsProxy) {
        PublicListsProxy.findAll().
            success(function(data) {
                $scope.lists = data;
            }).
            error(function(data, status) {
                alert('Error ' + status);
            });
    }
]);

controllers.controller('MovieSearchCtrl', ['$scope',
    '$routeParams', 'RottenTomatoesProxy',
    function($scope, $routeParams, RottenTomatoesProxy) {
        var search = function() {
            RottenTomatoesProxy.movieSearch($scope.q, $scope.pageSize, $scope.currentPage+1).
                success(function(data) {
                    $scope.count = data.total;
                    $scope.movies = data.movies;
                });
        };

        $scope.q = $routeParams.q;
        $scope.count = '0';
        $scope.pageSize = 5;
        $scope.currentPage = 0;
        search();
        
        $scope.$watch('pageSize', function() {
            search();
        });
        $scope.$watch('currentPage', function() {
            search();
        });
    }
]);

controllers.controller('MovieDetailCtrl', ['$scope',
    '$routeParams', 'RottenTomatoesProxy',
    function($scope, $routeParams, RottenTomatoesProxy) {
        RottenTomatoesProxy.movieDetail($routeParams.id).
            success(function(data) {
                $scope.movie = data;
            });
    }
]);
