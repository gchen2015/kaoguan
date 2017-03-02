(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('ActivityController', ActivityController);

    ActivityController.$inject = ['Activity'];

    function ActivityController(Activity) {

        var vm = this;

        vm.activities = [];

        loadAll();

        function loadAll() {
            Activity.query(function(result) {
                vm.activities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
