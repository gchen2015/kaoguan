(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('UploadDialogController', UploadDialogController);

    UploadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Upload'];

    function UploadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Upload) {
        var vm = this;

        vm.upload = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.upload.id !== null) {
                Upload.update(vm.upload, onSaveSuccess, onSaveError);
            } else {
                Upload.save(vm.upload, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kaoguanApp:uploadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.uploadedAt = false;
        vm.datePickerOpenStatus.completedAt = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
