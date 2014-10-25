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
