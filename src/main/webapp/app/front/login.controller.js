(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('FrontLoginController', FrontLoginController);

    FrontLoginController.$inject = ['$rootScope', '$cookies','$state', '$timeout', 'Auth'];

    function FrontLoginController ($rootScope, $cookies,$state, $timeout, Auth) {
        var vm = this;

        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;

        $timeout(function (){angular.element('#username').focus();});

        function cancel () {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;

        }

        function login (event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;

                $rootScope.globals = {
                                currentUser: {
                                    username: vm.username
                                }
                 };

                 var cookieExp = new Date();
                 cookieExp.setDate(cookieExp.getDate() + 7);
                 $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });

               // if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    //$state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('activity.person');
              //  }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is succesful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function register () {

            $state.go('kaoguan.register');
        }

        function requestResetPassword () {

            $state.go('requestReset');
        }
    }
})();
