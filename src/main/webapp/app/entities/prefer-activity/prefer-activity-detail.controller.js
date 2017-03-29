(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('PreferActivityDetailController', PreferActivityDetailController);

    PreferActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PreferActivity', 'User', 'Activity'];

    function PreferActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, PreferActivity, User, Activity) {
        var vm = this;

        vm.preferActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kaoguanApp:preferActivityUpdate', function(event, result) {
            vm.preferActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
