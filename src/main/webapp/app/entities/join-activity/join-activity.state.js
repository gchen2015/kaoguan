(function() {
    'use strict';

    angular
        .module('kaoguanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('join-activity', {
            parent: 'entity',
            url: '/join-activity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.joinActivity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/join-activity/join-activities.html',
                    controller: 'JoinActivityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('joinActivity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('join-activity-detail', {
            parent: 'join-activity',
            url: '/join-activity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kaoguanApp.joinActivity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/join-activity/join-activity-detail.html',
                    controller: 'JoinActivityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('joinActivity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'JoinActivity', function($stateParams, JoinActivity) {
                    return JoinActivity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'join-activity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('join-activity-detail.edit', {
            parent: 'join-activity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-activity/join-activity-dialog.html',
                    controller: 'JoinActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JoinActivity', function(JoinActivity) {
                            return JoinActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('join-activity.new', {
            parent: 'join-activity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-activity/join-activity-dialog.html',
                    controller: 'JoinActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDateTime: null,
                                delFlag: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('join-activity', null, { reload: 'join-activity' });
                }, function() {
                    $state.go('join-activity');
                });
            }]
        })
        .state('join-activity.edit', {
            parent: 'join-activity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-activity/join-activity-dialog.html',
                    controller: 'JoinActivityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JoinActivity', function(JoinActivity) {
                            return JoinActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('join-activity', null, { reload: 'join-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('join-activity.delete', {
            parent: 'join-activity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/join-activity/join-activity-delete-dialog.html',
                    controller: 'JoinActivityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JoinActivity', function(JoinActivity) {
                            return JoinActivity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('join-activity', null, { reload: 'join-activity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
