(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('UploadDetailController', UploadDetailController);

    UploadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Upload'];

    function UploadDetailController($scope, $rootScope, $stateParams, previousState, entity, Upload) {
        var vm = this;

        vm.upload = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kaoguanApp:uploadUpdate', function(event, result) {
            vm.upload = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
