(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = ['$translate', '$timeout', 'Auth','SmsService', 'LoginService','$scope','$interval','$stateParams'];

    function RegisterController ($translate, $timeout, Auth, SmsService, LoginService,$scope,$interval,$stateParams) {
        var vm = this;



         vm.sendSmsCode = sendSmsCode;
         vm.checkSmsCode = checkSmsCode;
         vm.phoneNumber = "";
         vm.inPutValidCode = "";
         vm.validRandomCode = "";

         vm.validResult = false;
         vm.validCode = false;
         vm.codeTitle = "获取验证码";
         vm.validCodeTitle = "验证";
         vm.code = 0;
         vm.second = 30;


         function randomPassword(length) {
             var chars = "1234567890";
             var pass = "";
             for (var x = 0; x < length; x++) {
                 var i = Math.floor(Math.random() * chars.length);
                 pass += chars.charAt(i);
             }
             return pass;
         }


         function checkSmsCode (validCode) {

           if(validCode == vm.validRandomCode) {
               vm.validResult = true;
           }else{
               vm.validResult = false;
           }

           console.log(vm.validResult);

         }


         function sendSmsCode (phoneNumber) {


           vm.validRandomCode = randomPassword(6);
           console.log("validRandomCode");
           console.log(vm.validRandomCode);
           //SmsService.send(phoneNumber)

            if(vm.code=='0'){
                     if (vm.validCode) {
                           vm.validCode = false;
                           var t = $interval(function () {
                           if(vm.second<=0){
                                        $interval.cancel(t);
                                        t = undefined;
                                        vm.second = 30;
                                        vm.codeTitle = "重发验证码";
                                        vm.validCode = true;
                            }else{
                                        vm.codeTitle = vm.second + "秒";
                                        vm.second--;
                            }
                         },1000,100);
                     }
              }

        }

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;

        $timeout(function (){
            angular.element('#login').focus();
         });

        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = $translate.use();
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
