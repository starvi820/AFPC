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
    $urlRouterProvider.otherwise('/base/cLicenseMgt');
    $stateProvider
      .state('base', {
        abstract: true,
        url: '/base',
        templateUrl:'/apps/views/cMain/base.html',
        controller: 'cMain.BaseCtrl'
      })
      .state('base.cLicenseMgt', {
        url: '/cLicenseMgt',
        templateUrl:'/apps/views/cConfig/licenseMgt.html',
        controller: 'cConfig.licenseMgtCtrl'
      })
      .state('base.cPatchMgt', {
        url: '/cPatchMgt',
        templateUrl:'/apps/views/cConfig/patchMgt.html',
        controller: 'cConfig.patchMgtCtrl'
      })
      .state('base.cDepartMgt', {
        url: '/cDepartMgt',
        templateUrl:'/apps/views/cConfig/departMgt.html',
        controller: 'cConfig.departMgtCtrl'
      })
       .state('base.cAdminMgt', {
        url: '/cAdminMgt',
        templateUrl:'/apps/views/cConfig/adminMgt.html',
        controller: 'cConfig.adminMgtCtrl'
      })
      .state('base.cHistoryMgt', {
        url: '/cHistoryMgt',
        templateUrl:'/apps/views/cConfig/historyMgt.html',
        controller: 'cConfig.historyMgtCtrl'
      })
  }]);