(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('UploadDeleteController',UploadDeleteController);

    UploadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Upload'];

    function UploadDeleteController($uibModalInstance, entity, Upload) {
        var vm = this;

        vm.upload = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Upload.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
