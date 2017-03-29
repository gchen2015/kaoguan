(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('PreferActivityController', PreferActivityController);

    PreferActivityController.$inject = ['PreferActivity'];

    function PreferActivityController(PreferActivity) {

        var vm = this;

        vm.preferActivities = [];

        loadAll();

        function loadAll() {
            PreferActivity.query(function(result) {
                vm.preferActivities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
