(function() {
    'use strict';

    angular
        .module('kaoguanApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ahdin',
            'flow'
        ])
        .config(['flowFactoryProvider', function (flowFactoryProvider) {
                                            	flowFactoryProvider.defaults = {
                                            			target: 'api/uploader',
                                            			testChunks: true,
                                            			forceChunkSize: true,
                                            			simultaneousUploads: 8,
                                            			singleFile: false,
                                            			permanentErrors:[415, 500, 501],
                                            			generateUniqueIdentifier: function () {
                                            				var request = new XMLHttpRequest();
                                            				request.open("GET","api/uploader/getUniqueIdentifier",false);
                                            				request.send();
                                            				return request.responseText;
                                            			}
                                            	};
                                            }])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
