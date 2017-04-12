(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .controller('UploadController', UploadController);

    UploadController.$inject = ['Upload'];

    function UploadController(Upload) {

        var vm = this;

        vm.uploads = [];

        loadAll();

        function loadAll() {
            Upload.query(function(result) {
                vm.uploads = result;
                vm.searchQuery = null;
            });
        }
    }
})();
