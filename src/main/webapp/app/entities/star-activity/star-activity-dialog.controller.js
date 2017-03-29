(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('StarActivityDialogController', StarActivityDialogController);

    StarActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StarActivity', 'User', 'Activity'];

    function StarActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StarActivity, User, Activity) {
        var vm = this;

        vm.starActivity = entity;
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
            if (vm.starActivity.id !== null) {
                StarActivity.update(vm.starActivity, onSaveSuccess, onSaveError);
            } else {
                StarActivity.save(vm.starActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kaoguanApp:starActivityUpdate', result);
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
