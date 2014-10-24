'use strict';

/*
 * Controllers
 */

var movieCatalogueControllers = angular.module('MovieCatalogueControllers', []);

movieCatalogueControllers.controller('NavigationCtrl', ['$scope', '$location',
    function($scope, $location) {
        $scope.navigate = function(url) {
            $location.path(url);
        };
    }]);

movieCatalogueControllers.controller('MovieListCtrl',['$scope', 'MovieCatalogueProxy',
    
    function($scope, MoviesCatalogueProxy) {
        $scope.pageSize = '4';
        $scope.currentPage = 0;
        MoviesCatalogueProxy.count()
                .success(function(max) {
                    $scope.max = max.value;
                }).error(function() {
            console.log("count: error");
        });
        getRange();
        $scope.$watch('currentPage', function() {
            getRange();
        });
        $scope.$watch('pageSize', function() {
            getRange();
        });
        function getRange() {
            var fst = $scope.pageSize * $scope.currentPage;
            MoviesCatalogueProxy.findRange(fst, $scope.pageSize)
                    .success(function(movies) {
                        $scope.movies = movies;
                    }).error(function() {
                console.log("findRange: error");
            });
        }
    }]);


movieCatalogueControllers.controller('MovieDetailCtrl', ['$scope',
    '$location', '$routeParams', 'MovieCatalogueProxy',
    function($scope, $location, $routeParams, MovieCatalogueProxy) {
        MovieCatalogueProxy.find($routeParams.id).
            success(function(movie) {
                $scope.movie = movie;
            }).
            error(function() {
                console.log("selectByPk: error");
            });

        // A listener
        $scope.update = function() {
            MovieCatalogueProxy.update($routeParams.id, $scope.movie)
                    .success(function() {
                        $location.path('/movies');
                    }).error(function() {
                ; // TODO;
            });
            //$location.path("movies");
        };
        // A listener
        $scope.delete = function() {
            // Really delete?? message
            MovieCatalogueProxy.delete($routeParams.id)
                    .success(function() {
                        $location.path('/movies');
                    }).error(function() {
                ; // TODO;
            });
        };
    }]);


movieCatalogueControllers.controller('MovieNewCtrl', ['$scope',
    '$location', 'MovieCatalogueProxy',
    function($scope, $location, MovieCatalogueProxy) {
        $scope.save = function() {
            MovieCatalogueProxy.create($scope.movie)
                    .success(function() {
                        $location.path('/movies');
                    }).error(function() {
                 console.log("save: error");

            });
        };
    }]);
