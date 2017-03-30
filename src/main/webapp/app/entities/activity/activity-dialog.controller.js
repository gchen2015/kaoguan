(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('ActivityDialogController', ActivityDialogController);

    ActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Activity', 'User','Upload', 'Ahdin'];

    function ActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Activity, User,Upload, Ahdin) {
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

       $scope.onFileSelect = function(uploadFile, name){

                	var activityId = 0;
                	if (vm.activity.id != null){
                		activityId = vm.activity.id;
                	}
                	var uploadImageFile = function(compressedBlob) {
                		Upload.upload({

                            url: '/api/postImage',
                            fields: { activityId: activityId },
                            file: compressedBlob,
                            method: 'POST'

                        }).progress(function (evt) {

                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            console.log('progress: ' + progressPercentage + '% ');

                        }).success(function (data, status, headers, config) {



                       	 if (name == "image1"){
                             vm.activity.image1 = data.image;
                          }
                          if (name == "image2"){
                             vm.activity.image2 = data.image;
                          }
                          if (name == "image3"){
                             vm.activity.image3 = data.image;
                          }
                          if (name == "image4"){
                             vm.activity.image4 = data.image;
                          }

                        }).error(function (data, status, headers, config) {

                            console.log('error status: ' + status);
                        });
                	};

                	//TODO gif no compress
               	 	Ahdin.compress({
        	              sourceFile: uploadFile[0],
        	              maxWidth: 1280,
        	              maxHeight:1000,
        	              quality: 0.8
        	          }).then(function(compressedBlob) {
        	        	  console.log('compressed image by ahdin.');
        	              uploadImageFile(compressedBlob);
        	          });
                };


        vm.datePickerOpenStatus.dateTime = false;
        vm.datePickerOpenStatus.createTime = false;
        vm.datePickerOpenStatus.updateTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
