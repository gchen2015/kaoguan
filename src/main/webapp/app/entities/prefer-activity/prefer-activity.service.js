(function() {
    'use strict';
    angular
        .module('kaoguanApp')
        .factory('PreferActivity', PreferActivity);

    PreferActivity.$inject = ['$resource', 'DateUtils'];

    function PreferActivity ($resource, DateUtils) {
        var resourceUrl =  'api/prefer-activities/:id';

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
