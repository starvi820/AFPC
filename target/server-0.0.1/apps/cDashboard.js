 angular.module('AfpcApp', [
    'ui.router',
    'ui.bootstrap',
    'ngResource',
    'ngSanitize',
    'ngCookies',
    'ngTouch',
    'ngAnimate',
    'ngFileUpload'
    ])
  .config(['$stateProvider', '$urlRouterProvider','$locationProvider',
           function($stateProvider, $urlRouterProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/base/dashboard');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.dashboard', {
        url: '/dashboard',
        templateUrl:'/apps/views/cDashboard/dashboard.html',
        controller: 'cDashbaord.DashboardCtrl'
      })
     
      
  }]);