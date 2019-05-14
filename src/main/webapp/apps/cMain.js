 angular.module('AfpcApp', [
    'ui.router',
    'ui.bootstrap',
    'ngResource',
    'ngSanitize',
    'ngCookies',
    'ngTouch',
    'ngAnimate',
    'ngFileUpload',
    'nvd3'
    ])
  .config(['$stateProvider', '$urlRouterProvider','$locationProvider', 
           function($stateProvider, $urlRouterProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/base/main');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.main', {
        url: '/main',
        templateUrl:'/apps/views/cMain/main.html',
        controller: 'cMain.MainCtrl'
      })
  }]);