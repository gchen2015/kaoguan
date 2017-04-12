(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .factory('ActivityService', ActivityService);

    ActivityService.$inject = ['$rootScope', '$http'];

    function ActivityService ($rootScope, $http) {
        var service = {
            findAllActivities: findAllActivities
        };

        return service;

        function findAllActivities (done) {

            return $http.get('api/activities/news').then(function (response) {
                return done(response.data);
            });
        }

    }

     angular
        .module('kaoguanApp')
        .factory('CommentService', CommentService);

     CommentService.$inject = ['$rootScope', '$http'];

      function CommentService ($rootScope, $http) {
            var service = {
                findAllComments: findAllComments
            };

            return service;

            function findAllComments (done) {

                return $http.get('api/comments/news').then(function (response) {
                    return done(response.data);
                });
            }

        }


})();

