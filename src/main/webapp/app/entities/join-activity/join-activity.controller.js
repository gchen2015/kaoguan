(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('JoinActivityController', JoinActivityController);

    JoinActivityController.$inject = ['JoinActivity'];

    function JoinActivityController(JoinActivity) {

        var vm = this;

        vm.joinActivities = [];

        loadAll();

        function loadAll() {
            JoinActivity.query(function(result) {
                vm.joinActivities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
