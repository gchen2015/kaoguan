(function() {
    'use strict';
    angular
        .module('kaoguanApp')
        .factory('StarActivity', StarActivity);

    StarActivity.$inject = ['$resource', 'DateUtils'];

    function StarActivity ($resource, DateUtils) {
        var resourceUrl =  'api/star-activities/:id';

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
