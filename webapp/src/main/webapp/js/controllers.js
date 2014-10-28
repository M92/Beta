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

controllers.controller('PublicListDetailCtrl', ['$scope',
    '$routeParams', 'UserRegistryProxy', 'ListCatalogueProxy',
    function($scope, $routeParams, UserRegistryProxy, ListCatalogueProxy) {
        var findAllLists = function() {
            UserRegistryProxy.findAllMovieLists($scope.currentUser).
                success(function(data) {
                    $scope.lists = data;
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
        if ($scope.isLoggedIn) {
            findAllLists();
        }
        ListCatalogueProxy.findList($routeParams.id).
            success(function(data) {
                $scope.list = data;
            });
        $scope.addToList = function(list, movie) {
            var jsonMovie = { 'id':movie.foreignId, 'title':movie.title,
                              'year':movie.releaseYear, 'runtime':movie.runTime };
            UserRegistryProxy.addMovieToList($scope.currentUser, list, jsonMovie).
                success(function() {
                    //
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

controllers.controller('UserListDetailCtrl', ['$scope',
    '$location', '$routeParams', 'UserRegistryProxy',
    function($scope, $location, $routeParams, UserRegistryProxy) {
        var listname;
        var visibility;
        var findList = function() {
            UserRegistryProxy.findMovieList($routeParams.user, $routeParams.id).
                success(function(data) {
                    $scope.list = data;
                    listname = data.name;
                    visibility = data.visibility;
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
        findList();
        $scope.renameList = function(isValid) {
            if (isValid) {
                UserRegistryProxy.editMovieList($routeParams.user, $routeParams.id, $scope.listname, visibility).
                    success(function() {
                        findList();
                    }).
                    error(function(data, status) {
                        alert('Error ' + status);
                    });
                }
        };
        $scope.changeVisibility = function(newVisibility) {
            UserRegistryProxy.editMovieList($routeParams.user, $routeParams.id, listname, newVisibility).
                success(function() {
                    findList();
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
        $scope.deleteList = function() {
            UserRegistryProxy.deleteMovieList($routeParams.user, $routeParams.id).
                success(function() {
                    $location.path($scope.currentUser + '/lists');
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
        $scope.removeMovie = function(movie) {
            UserRegistryProxy.removeMovieFromList($routeParams.user, $routeParams.id, movie).
                success(function() {
                    findList();
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
    }
]);

controllers.controller('UserListsCtrl', ['$scope',
    '$routeParams', 'UserRegistryProxy',
    function($scope, $routeParams, UserRegistryProxy) {
        var findAll = function() {
            UserRegistryProxy.findAllMovieLists($routeParams.user).
                success(function(data) {
                    $scope.lists = data;
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
        findAll();
        $scope.addMovieList = function(isValid) {
            if (isValid) {
                UserRegistryProxy.addMovieList($routeParams.user, $scope.listname).
                    success(function() {
                        findAll();
                    }).
                    error(function(data, status) {
                        alert('Error ' + status);
                    });
                }
        };
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

controllers.controller('MovieSearchCtrl', ['$scope',
    '$location', '$routeParams', 'UserRegistryProxy', 'RottenTomatoesProxy',
    function($scope, $location, $routeParams, UserRegistryProxy, RottenTomatoesProxy) {
        var search = function() {
            RottenTomatoesProxy.movieSearch($scope.q, $scope.pageSize, $scope.currentPage+1).
                success(function(data) {
                    $scope.count = data.total;
                    $scope.movies = data.movies;
                });
        };
        var findAllLists = function() {
            UserRegistryProxy.findAllMovieLists($scope.currentUser).
                success(function(data) {
                    $scope.lists = data;
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
        $scope.q = $routeParams.q;
        $scope.count = '0';
        $scope.pageSize = 5;
        $scope.currentPage = 0;
        search();
        if ($scope.isLoggedIn) {
            findAllLists();
        }
        $scope.$watch('pageSize', function() {
            search();
        });
        $scope.$watch('currentPage', function() {
            search();
        });
        $scope.addToList = function(list, movie) {
            var runtime = movie.runtime;
            if (!runtime) { runtime = 0; }
            var jsonMovie = { 'id':movie.id, 'title':movie.title,
                              'year':movie.year, 'runtime':runtime };
            UserRegistryProxy.addMovieToList($scope.currentUser, list, jsonMovie).
                success(function() {
                    $location.path($scope.currentUser + '/lists/' + list);
                }).
                error(function(data, status) {
                    alert('Error ' + status);
                });
        };
    }
]);
