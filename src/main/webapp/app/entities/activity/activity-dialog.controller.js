(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('ActivityDialogController', ActivityDialogController);

    ActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Activity', 'User'];

    function ActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Activity, User) {
        var vm = this;

        vm.activity = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.activity.id !== null) {
                Activity.update(vm.activity, onSaveSuccess, onSaveError);
            } else {
                Activity.save(vm.activity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kaoguanApp:activityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

         $scope.fileSuccess = function ($flow, $file, $message,name) {
                  console.log("fileSuccess");
                  console.log($flow);

                  $scope.flow = $flow;

                  if (name == "image1"){

                          vm.activity.image1 = $flow.files[0].uniqueIdentifier;
                          console.log(vm.activity.image1);
                  }
        	      if (name == "image2"){
                           vm.activity.image2 = $flow.files[0].uniqueIdentifier;
                           console.log(vm.activity.image2);
                  }
                  if (name == "image3"){
                            vm.activity.image3 = $flow.files[0].uniqueIdentifier;
                            console.log(vm.activity.image3);
                  }
                  if (name == "image4"){
                            vm.activity.image4 = $flow.files[0].uniqueIdentifier;
                            console.log(vm.activity.image4);
                  }

         };

        vm.datePickerOpenStatus.datetime = false;
        vm.datePickerOpenStatus.createTime = false;
        vm.datePickerOpenStatus.updateTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
