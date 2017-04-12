(function () {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('PersonHomeController', PersonHomeController);

    PersonHomeController.$inject = ['$rootScope','$scope','$cookies','Principal','$state'];
    function PersonHomeController($rootScope,$scope,$cookies,Principal,$state) {
        var vm = this;

        vm.user = null;


         //initController();
        console.debug('Principal.isAuthenticate');
         var isAuthenticated =  Principal.isAuthenticate
         console.debug(vm.isAuthenticated);


        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
           var currentUser =  $rootScope.globals.currentUser.username


           console.debug("###############")
           console.debug(currentUser);
           console.debug("###############")
        }else{
            $state.go('activity.login');
        }

         //if (Principal.isAuthenticated) {
          //  $state.go('book.login');
         //}

         $scope.$on('authenticationSuccess', function() {
            getAccount();
         });

         getAccount();

          console.debug('vm.user1');
          console.debug(vm.user);

        function getAccount() {
             Principal.identity().then(function(account) {
                vm.account = account;
                vm.user = account
                console.debug('vm.user2');
                console.debug(vm.user);

                vm.isAuthenticated = Principal.isAuthenticated;
             });
        }

        function initController() {
            //loadCurrentUser();
        }



    }

})();
