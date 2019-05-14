'use strict';
angular.module('AfpcApp')
.directive('header', ['headerConfig', function (headerConfig){
	var thisController = ['$scope', '$timeout', '$http', '$location', '$window', '$modal', 
	                      function($scope, $timeout, $http, $location, $window, $modal){
		
		$http({
			method:'GET',
			url:'/cMain/getUserInfo'
		}).success(function(data){
			$scope.userInfo = data;
			console.log(data);
		}).error(function() {
			alert("error");
		}); 
		
		
		
		
	}];
	return {
		templateUrl: '/apps/commons/views/header.html',
		restrict: 'E', 
		replace: true, 
		controller: thisController,
		link: function(scope){
			scope.headerUrl = headerConfig.headerUrl;
			scope.headerTitle = headerConfig.headerTitle;
		}
	}
}])
;

