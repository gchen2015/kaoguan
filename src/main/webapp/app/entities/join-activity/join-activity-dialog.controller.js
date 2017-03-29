(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('JoinActivityDialogController', JoinActivityDialogController);

    JoinActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JoinActivity', 'User', 'Activity'];

    function JoinActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JoinActivity, User, Activity) {
        var vm = this;

        vm.joinActivity = entity;
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
            if (vm.joinActivity.id !== null) {
                JoinActivity.update(vm.joinActivity, onSaveSuccess, onSaveError);
            } else {
                JoinActivity.save(vm.joinActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kaoguanApp:joinActivityUpdate', result);
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
