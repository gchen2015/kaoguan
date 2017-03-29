(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('CommentDetailController', CommentDetailController);

    CommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comment', 'User', 'Activity'];

    function CommentDetailController($scope, $rootScope, $stateParams, previousState, entity, Comment, User, Activity) {
        var vm = this;

        vm.comment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kaoguanApp:commentUpdate', function(event, result) {
            vm.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
