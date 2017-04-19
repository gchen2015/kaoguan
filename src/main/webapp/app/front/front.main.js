(function() {
    //'use strict';

    angular.module('kaoguanApp').config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fronthome', {

                      url: '/',
                        data: {
                         authorities: []
                        },
                      views: {
                         'content@': {
                          templateUrl: 'app/front/main.html',
                          controller: 'FrontMainController',
                          controllerAs: 'vm'
                       }
            }
        }).state('activity.details', {

                        url: 'front/activity/{id}',
                        data: {

                            pageTitle: '详情'
                        },
                        views: {
                            'content@': {
                                templateUrl: 'app/front/activity-detail.html',
                                controller: 'FrontActivityDetailController',
                                controllerAs: 'vm'
                            }
                        },
                        resolve: {
                        },
                         hideNavbar: true
           }).state('activity.login', {

                         url: 'front/login',
                         data: {

                               pageTitle: '登陆'
                          },
                          views: {
                               'content@': {
                                   templateUrl: 'app/front/login.html',
                                   controller: 'FrontLoginController',
                                   controllerAs: 'vm'
                           }
                         },
                         resolve: {
                         },
                           hideNavbar: true
            }).state('activity.create', {

                                       url: 'front/activityCreate',
                                       data: {

                                             pageTitle: '发布活动'
                                        },
                                        views: {
                                             'content@': {
                                                 templateUrl: 'app/front/activityCreate.html',
                                                 controller: 'FrontActivityCreateController',
                                                 controllerAs: 'vm'
                                         }
                                       },
                                       resolve: {
                                       }
             })
            .state('activity.person', {

                         url: '/front/person',
                         data: {

                               pageTitle: '个人信息'
                         },
                         views: {
                                 'content@': {
                                      templateUrl: 'app/front/person.html',
                                      controller: 'PersonHomeController',
                                      controllerAs: 'vm'
                                 }
                          },
                         resolve: {

                        },
                         hideNavbar: true
           }) .state('activity.personadmin', {

                                      url: '/front/person2',
                                      data: {

                                            pageTitle: '个人信息'
                                      },
                                      views: {
                                              'content@': {
                                       templateUrl: 'app/home/home.html',
                                       controller: 'HomeController',
                                                   controllerAs: 'vm'
                                              }
                                       },
                                      resolve: {

                                     }
           }).state('activity.comment', {

                                      url: '/front/comment/{id}',
                                      data: {

                                            pageTitle: '评论详情'
                                      },
                                      views: {
                                              'content@': {
                                                   templateUrl: 'app/front/plxx.html',
                                                   controller: 'FrontActivityCommentController',
                                                   controllerAs: 'vm'
                                              }
                                       }
                                       ,
                                         hideNavbar: true


            }).state('activity.register', {

                          url: '/front/register',
                          data: {
                                  authorities: [],
                                   pageTitle: 'Registration'
                          },
                          views: {
                                    'content@': {
                                       templateUrl: 'app/account/register/register.html',
                                       controller: 'RegisterController',
                                       controllerAs: 'vm'
                                     }
                         },
                          hideNavbar: true
            })

        ;
    }
})();
