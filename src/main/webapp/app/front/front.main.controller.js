

(function() {
    'use strict';


    angular
            .module('kaoguanApp')
            .factory('ActivityScrollService', ActivityScrollService);

    ActivityScrollService.$inject = ['$rootScope', '$http','ActivityService'];

             function ActivityScrollService ($rootScope, $http,ActivityService) {

                  $rootScope.ageTemplateSelected = {value :""};
                  $rootScope.locationTemplateSelected = {value :""};


                   var ActivityScrollService = function() {
                      this.items = [];
                      this.busy = false;
                      this.after = '';
                    };


                   ActivityScrollService.prototype.nextPage = function () {

                        // busy - stop
                        if (this.busy == true) {

                            return;
                        }

                        // busy now
                        this.busy = true;


                        //this.offset = 0;
                        ActivityService.findAllActivitiesInfiniteScroll(
                             this.offset,$rootScope.ageTemplateSelected.value,$rootScope.locationTemplateSelected.value, function (response) {

                            // stop loading if no data returned
                            if(response.length == 0) {

                                return;
                            }

                            var _this = this;
                            angular.forEach(response, function (a_visit) {
                                _this.items.push(a_visit);
                            });

                            // set the last acquired record value
                            this.offset = this.items[this.items.length - 1].id;

                            // not busy
                            this.busy = false;
                        }.bind(this));
                    };

                    return ActivityScrollService;

                }

    angular
        .module('kaoguanApp')
        .controller('FrontMainController', FrontMainController);





    FrontMainController.$inject = ['$rootScope','$scope', '$http', 'Principal', 'LoginService', 'ActivityService','$state','ActivityScrollService'];

    function FrontMainController ($rootScope, $scope, $http, Principal, LoginService,ActivityService, $state,ActivityScrollService) {
        var vm = this;

        console.debug('FrontMainController start');

        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        $scope.activityScrollService = new ActivityScrollService();


        $scope.ageTemplates = [{id:1,name:"0-1岁",value:"0-1"},{id:2,name:"1-3岁",value:"1-3"},{id:3,name:"3-6岁",value:"3-6"}];

        $scope.locationTemplates = [{id:1,name:"上海",value:"上海"},{id:2,name:"北京",value:"北京"},{id:3,name:"深圳",value:"深圳"}];

        $scope.changedValue = function(item) {


               if($scope.ageTemplateSelected  == null) {

                 $scope.ageTemplateSelected = {value :""};
               }

               if($scope.locationTemplateSelected  == null) {

                 $scope.locationTemplateSelected = {value :""};
               }

               ActivityService.findAllActivitiesByAgeAndCity($scope.ageTemplateSelected.value,$scope.locationTemplateSelected.value,function(result){

                           $scope.activityScrollService.items = result;

               });
        }



        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }


        ActivityService.findAllActivities(function(result){
            console.debug("result:")
            console.debug(result)
    		$scope.newActivities = result;
    	});

    	$scope.jump = function(link){
    		window.location.href = link;
    	};


        console.debug('FrontMainController end');
    }

       angular
            .module('kaoguanApp')
            .controller('FrontActivityDetailController', FrontActivityDetailController);


     FrontActivityDetailController.$inject = ['$rootScope','$scope', '$http', 'Principal', 'Activity', 'ActivityService','$state','$stateParams'];

     function FrontActivityDetailController ($rootScope, $scope, $http, Principal, Activity,ActivityService, $state, $stateParams) {
             var vm = this;

              console.debug('FrontActivityDetailController start');

              Activity.get({id: $stateParams.id}, function(result) {
                      $scope.activity = result;
              });

             console.debug('FrontActivityDetailController end');

      }

       angular
              .module('kaoguanApp')
              .controller('FrontActivityCreateController', FrontActivityCreateController);


      FrontActivityCreateController.$inject = ['$rootScope','$scope', '$cookies','$http', 'Principal','Activity', 'ActivityService','$state','$stateParams','FlashService'];

          function FrontActivityCreateController ($rootScope, $scope, $cookies, $http, Principal,Activity,ActivityService, $state, $stateParams,FlashService) {
                    var vm = this;

                    vm.activity = {};
                    vm.datePickerOpenStatus = {};


                   vm.openCalendar = openCalendar;
                   vm.save = save;

                   console.debug('FrontActivityCreateController start');


                   function save () {
                      vm.isSaving = true;
                      Activity.save(vm.activity, onSaveSuccess, onSaveError);

                   }
                   function onSaveSuccess (result) {
                            //$scope.$emit('kaoguanApp:activityUpdate', result);

                       vm.isSaving = false;
                        FlashService.Success('Activity save successful', true);
                         $state.go('activity.person');
                    }

                   function onSaveError () {
                        vm.isSaving = false;
                        FlashService.Error('Activity save failed!');
                   }

                   function openCalendar (date) {
                        vm.datePickerOpenStatus[date] = true;
                   }

                   /*
                        * For when the file upload completes.
                        */

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

                   /*
                        * Due to difficulties with ng-flow/flowjs sending the right CSRF token
                        * in the headers, we will just force it here as a query parameter since
                        * this is allowed in Spring Security.
                        *
                        * NOTE: This could have been done with the query option to the flow-init.
                        */
                    $scope.uploaderTarget = function() {
                       	var rtn = 'api/uploader?_csrf=' + $cookies['CSRF-TOKEN'];
                       	console.log(rtn);
                       	return rtn;
                    };

                    function openCalendar (date) {
                                vm.datePickerOpenStatus[date] = true;
                     }

                  console.debug('FrontActivityCreateController end');

           }



         angular
            .module('kaoguanApp')
            .controller('FrontActivityCommentController', FrontActivityCommentController);


         FrontActivityCommentController.$inject = ['$rootScope','$scope', '$http', 'Principal', 'Comment', 'CommentService','$state','$stateParams','moment'];


          function FrontActivityCommentController ($rootScope, $scope, $http, Principal,Comment,CommentService, $state,$stateParams,moment) {
              var vm = this;

                $scope.state = $state;

              $scope.message = {
                 text: 'hello world!',
                 time: new Date()
              };

              vm.save = save ;
              console.debug('FrontActivityCommentController start');
              console.debug($stateParams.id)

              CommentService.findAllComments(function(result){
                  console.debug("result:")
                  console.debug(result)
          		  $scope.allComments = result;
          	  });
            console.debug('FrontActivityCommentController end');

             function save () {
                  vm.isSaving = true;

                  Comment.save(vm.comment, onSaveSuccess, onSaveError);

            }

            function onSaveSuccess (result) {
                  $scope.$emit('kaoguanApp:commentUpdate', result);
                  $scope.allComments.push(result);
                  vm.isSaving = false;
             }

             function onSaveError () {
                   vm.isSaving = false;
              }
          }


})();
