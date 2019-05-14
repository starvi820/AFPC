 angular.module('AfpcApp', [
    'ui.router',
    'ui.bootstrap',
    'ngResource',
    'ngSanitize',
    'ngCookies',
    'ngTouch',
    'ngAnimate',
    'ngFileUpload',
    'textAngular',
    ])
  .config(['$stateProvider', '$urlRouterProvider','$locationProvider', 
           function($stateProvider, $urlRouterProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/base/cList');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.cList', {
        url: '/cList',
        templateUrl:'/apps/views/cList/list.html',
        controller: 'cList.listCtrl'
      })
  }]);