(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('JoinActivityDetailController', JoinActivityDetailController);

    JoinActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JoinActivity', 'User', 'Activity'];

    function JoinActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, JoinActivity, User, Activity) {
        var vm = this;

        vm.joinActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kaoguanApp:joinActivityUpdate', function(event, result) {
            vm.joinActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
