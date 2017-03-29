(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('StarActivityController', StarActivityController);

    StarActivityController.$inject = ['StarActivity'];

    function StarActivityController(StarActivity) {

        var vm = this;

        vm.starActivities = [];

        loadAll();

        function loadAll() {
            StarActivity.query(function(result) {
                vm.starActivities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
