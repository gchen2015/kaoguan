(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('PreferActivityDeleteController',PreferActivityDeleteController);

    PreferActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'PreferActivity'];

    function PreferActivityDeleteController($uibModalInstance, entity, PreferActivity) {
        var vm = this;

        vm.preferActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PreferActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
