(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('StarActivityDeleteController',StarActivityDeleteController);

    StarActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'StarActivity'];

    function StarActivityDeleteController($uibModalInstance, entity, StarActivity) {
        var vm = this;

        vm.starActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StarActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
