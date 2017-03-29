(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('star-activity', {
            parent: 'entity',
            url: '/star-activity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.starActivity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/star-activity/star-activities.html',
                    controller: 'StarActivityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('starActivity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('star-activity-detail', {
            parent: 'star-activity',
            url: '/star-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.starActivity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/star-activity/star-activity-detail.html',
                    controller: 'StarActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('starActivity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StarActivity', function($stateParams, StarActivity) {
                    return StarActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'star-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('star-activity-detail.edit', {
            parent: 'star-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/star-activity/star-activity-dialog.html',
                    controller: 'StarActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StarActivity', function(StarActivity) {
                            return StarActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('star-activity.new', {
            parent: 'star-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/star-activity/star-activity-dialog.html',
                    controller: 'StarActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                delFlag: null,
                                createDateTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('star-activity', null, { reload: 'star-activity' });
                }, function() {
                    $state.go('star-activity');
                });
            }]
        })
        .state('star-activity.edit', {
            parent: 'star-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/star-activity/star-activity-dialog.html',
                    controller: 'StarActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StarActivity', function(StarActivity) {
                            return StarActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('star-activity', null, { reload: 'star-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('star-activity.delete', {
            parent: 'star-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/star-activity/star-activity-delete-dialog.html',
                    controller: 'StarActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StarActivity', function(StarActivity) {
                            return StarActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('star-activity', null, { reload: 'star-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
