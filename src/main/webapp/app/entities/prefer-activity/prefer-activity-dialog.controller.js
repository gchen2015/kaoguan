(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('PreferActivityDialogController', PreferActivityDialogController);

    PreferActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PreferActivity', 'User', 'Activity'];

    function PreferActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PreferActivity, User, Activity) {
        var vm = this;

        vm.preferActivity = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.activities = Activity.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.preferActivity.id !== null) {
                PreferActivity.update(vm.preferActivity, onSaveSuccess, onSaveError);
            } else {
                PreferActivity.save(vm.preferActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kaoguanApp:preferActivityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDateTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
