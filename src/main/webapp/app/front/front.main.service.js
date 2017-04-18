(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .factory('ActivityService', ActivityService);

    ActivityService.$inject = ['$rootScope', '$http'];

    function ActivityService ($rootScope, $http) {
        var service = {
            findAllActivities: findAllActivities,
            findAllActivitiesInfiniteScroll : findAllActivitiesInfiniteScroll,
            findAllActivitiesByAgeAndCity : findAllActivitiesByAgeAndCity
        };

        return service;

        function findAllActivities (done) {

            return $http.get('api/activities').then(function (response) {
                return done(response.data);
            });
        }

        function findAllActivitiesInfiniteScroll (after_id,ageRanger,city,done) {

             return $http.get('api/activities/infiniteScroll?afterId='+after_id + '&ageRanger='+ageRanger + '&city='+city).then(function (response) {
                        return done(response.data);
             });
        }

        function findAllActivitiesByAgeAndCity(ageRange,city,done) {
              return $http.get('api/activitiesByAgeAndCity?ageRange='+ageRange+'&city='+city).then(function (response) {
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

                return $http.get('api/comments').then(function (response) {
                    return done(response.data);
                });
            }

        }


})();

