(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('JoinActivityDeleteController',JoinActivityDeleteController);

    JoinActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'JoinActivity'];

    function JoinActivityDeleteController($uibModalInstance, entity, JoinActivity) {
        var vm = this;

        vm.joinActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            JoinActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
