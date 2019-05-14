 angular.module('AfpcApp', [
    'ui.router',
    'ui.bootstrap',
    'ngResource',
    'ngSanitize',
    'ngCookies',
    'ngTouch',
    'ngAnimate',
    'ngFileUpload',
    'ui.calendar',
    'daterangepicker'
    ])
  .config(['$stateProvider', '$urlRouterProvider','$locationProvider', 
           function($stateProvider, $urlRouterProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/base/cSchedule');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.cSchedule', {
        url: '/cSchedule',
        templateUrl:'/apps/views/cCheck/schedule.html',
        controller: 'cCheck.scheduleCtrl'
      })
      .state('base.cResult', {
        url: '/cResult',
        templateUrl:'/apps/views/cCheck/result.html',
        controller: 'cCheck.resultCtrl'
      })
  }]);