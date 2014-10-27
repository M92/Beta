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
    '$location', '$routeParams', 'UserRegistryProxy',
    function($scope, $location, $routeParams, UserRegistryProxy) {
        $scope.addMovieList = function() {
            UserRegistryProxy.addMovieList($routeParams.user, $scope.name).
                success(function(data) {
                    $location.path($routeParams.user + '/lists/' + data.id);
                    $scope.lists = data
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
    }
]);

controllers.controller('PublicListsCtrl', ['$scope',
    'ListCatalogueProxy',
    function($scope, ListCatalogueProxy) {
        ListCatalogueProxy.findAll().
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

controllers.controller('MovieListCtrl', ['$scope',
    '$routeParams', 'ListCatalogueProxy',
    function($scope, $routeParams, ListCatalogueProxy) {
        ListCatalogueProxy.findList($routeParams.id).
            success(function(data) {
                $scope.list = data;
            });
    }
]);
