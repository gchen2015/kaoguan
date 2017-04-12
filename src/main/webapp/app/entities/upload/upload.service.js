(function() {
    'use strict';
    angular
        .module('kaoguanApp')
        .factory('Upload', Upload);

    Upload.$inject = ['$resource', 'DateUtils'];

    function Upload ($resource, DateUtils) {
        var resourceUrl =  'api/uploads/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.uploadedAt = DateUtils.convertLocalDateFromServer(data.uploadedAt);
                        data.completedAt = DateUtils.convertLocalDateFromServer(data.completedAt);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.uploadedAt = DateUtils.convertLocalDateToServer(copy.uploadedAt);
                    copy.completedAt = DateUtils.convertLocalDateToServer(copy.completedAt);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.uploadedAt = DateUtils.convertLocalDateToServer(copy.uploadedAt);
                    copy.completedAt = DateUtils.convertLocalDateToServer(copy.completedAt);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
