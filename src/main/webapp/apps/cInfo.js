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
    $urlRouterProvider.otherwise('/base/cInfo');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.cInfo', {
        url: '/cInfo',
        templateUrl:'/apps/views/cInfo/info.html',
        controller: 'cInfo.infoCtrl'
      })
      .state('base.cInfoView', {
        url: '/cInfoView',
        templateUrl:'/apps/views/cInfo/infoView.html',
        controller: 'cInfo.cInfoViewCtrl'
      }) .state('base.cInfoRgt', {
          url: '/cInfoRgt',
          templateUrl:'/apps/views/cInfo/infoRgt.html',
          controller: 'cInfo.cInfoRgtCtrl'
        })
  }]);