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
    'chart.js',
    'daterangepicker'
    ])
  .config(['$stateProvider', '$urlRouterProvider','$locationProvider', 
           function($stateProvider, $urlRouterProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/base/cChart');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.cChart', {
        url: '/cChart',
        templateUrl:'/apps/views/cResult/chart.html',
        controller: 'cResult.chartCtrl'
      })
      .state('base.cReport', {
        url: '/cReport',
        templateUrl:'/apps/views/cResult/report.html',
        controller: 'cResult.reportCtrl'
      })
  }]);