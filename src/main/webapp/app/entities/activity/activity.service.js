(function() {
    'use strict';
    angular
        .module('kaoguanApp')
        .factory('Activity', Activity);

    Activity.$inject = ['$resource', 'DateUtils'];

    function Activity ($resource, DateUtils) {
        var resourceUrl =  'api/activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateTime = DateUtils.convertLocalDateFromServer(data.dateTime);
                        data.createTime = DateUtils.convertLocalDateFromServer(data.createTime);
                        data.updateTime = DateUtils.convertLocalDateFromServer(data.updateTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateTime = DateUtils.convertLocalDateToServer(copy.dateTime);
                    copy.createTime = DateUtils.convertLocalDateToServer(copy.createTime);
                    copy.updateTime = DateUtils.convertLocalDateToServer(copy.updateTime);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateTime = DateUtils.convertLocalDateToServer(copy.dateTime);
                    copy.createTime = DateUtils.convertLocalDateToServer(copy.createTime);
                    copy.updateTime = DateUtils.convertLocalDateToServer(copy.updateTime);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
