(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('upload', {
            parent: 'entity',
            url: '/upload',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.upload.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/upload/uploads.html',
                    controller: 'UploadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('upload');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('upload-detail', {
            parent: 'upload',
            url: '/upload/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.upload.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/upload/upload-detail.html',
                    controller: 'UploadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('upload');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Upload', function($stateParams, Upload) {
                    return Upload.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'upload',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('upload-detail.edit', {
            parent: 'upload-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload/upload-dialog.html',
                    controller: 'UploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Upload', function(Upload) {
                            return Upload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('upload.new', {
            parent: 'upload',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload/upload-dialog.html',
                    controller: 'UploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                originalName: null,
                                uploadedAt: null,
                                md5sum: null,
                                uploadComplete: null,
                                totalChunks: null,
                                totalSize: null,
                                completedAt: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('upload', null, { reload: 'upload' });
                }, function() {
                    $state.go('upload');
                });
            }]
        })
        .state('upload.edit', {
            parent: 'upload',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload/upload-dialog.html',
                    controller: 'UploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Upload', function(Upload) {
                            return Upload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('upload', null, { reload: 'upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('upload.delete', {
            parent: 'upload',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload/upload-delete-dialog.html',
                    controller: 'UploadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Upload', function(Upload) {
                            return Upload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('upload', null, { reload: 'upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
