(function() {
    'use strict';
    angular
        .module('kaoguanApp')
        .factory('JoinActivity', JoinActivity);

    JoinActivity.$inject = ['$resource', 'DateUtils'];

    function JoinActivity ($resource, DateUtils) {
        var resourceUrl =  'api/join-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDateTime = DateUtils.convertDateTimeFromServer(data.createDateTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
