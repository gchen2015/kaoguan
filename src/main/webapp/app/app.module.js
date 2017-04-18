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
            'flow',
            'angularMoment'
        ])
        .constant('angularMomentConfig', {
              timezone: 'Asia/Shanghai' // e.g. 'Europe/London'
          })
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

    run.$inject = ['stateHandler', 'translationHandler','amMoment'];

    function run(stateHandler, translationHandler,amMoment) {
        stateHandler.initialize();
        translationHandler.initialize();
        amMoment.changeLocale('zh-cn');
    }
})();
