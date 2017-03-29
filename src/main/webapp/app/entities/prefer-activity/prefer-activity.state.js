(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prefer-activity', {
            parent: 'entity',
            url: '/prefer-activity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.preferActivity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prefer-activity/prefer-activities.html',
                    controller: 'PreferActivityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preferActivity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('prefer-activity-detail', {
            parent: 'prefer-activity',
            url: '/prefer-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.preferActivity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prefer-activity/prefer-activity-detail.html',
                    controller: 'PreferActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preferActivity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PreferActivity', function($stateParams, PreferActivity) {
                    return PreferActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prefer-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prefer-activity-detail.edit', {
            parent: 'prefer-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefer-activity/prefer-activity-dialog.html',
                    controller: 'PreferActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreferActivity', function(PreferActivity) {
                            return PreferActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prefer-activity.new', {
            parent: 'prefer-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefer-activity/prefer-activity-dialog.html',
                    controller: 'PreferActivityDialogController',
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
                    $state.go('prefer-activity', null, { reload: 'prefer-activity' });
                }, function() {
                    $state.go('prefer-activity');
                });
            }]
        })
        .state('prefer-activity.edit', {
            parent: 'prefer-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefer-activity/prefer-activity-dialog.html',
                    controller: 'PreferActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreferActivity', function(PreferActivity) {
                            return PreferActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prefer-activity', null, { reload: 'prefer-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prefer-activity.delete', {
            parent: 'prefer-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefer-activity/prefer-activity-delete-dialog.html',
                    controller: 'PreferActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PreferActivity', function(PreferActivity) {
                            return PreferActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prefer-activity', null, { reload: 'prefer-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
