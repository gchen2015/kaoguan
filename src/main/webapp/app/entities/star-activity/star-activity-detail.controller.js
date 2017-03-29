(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('StarActivityDetailController', StarActivityDetailController);

    StarActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StarActivity', 'User', 'Activity'];

    function StarActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, StarActivity, User, Activity) {
        var vm = this;

        vm.starActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kaoguanApp:starActivityUpdate', function(event, result) {
            vm.starActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
