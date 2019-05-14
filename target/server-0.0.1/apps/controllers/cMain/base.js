'use strict';
angular.module('AfpcApp')
  .controller('cMain.BaseCtrl',['$scope', '$location', function( $scope, $location) {
	  
	  console.log('location: ' +  $location.absUrl()  );
	  
  }]);