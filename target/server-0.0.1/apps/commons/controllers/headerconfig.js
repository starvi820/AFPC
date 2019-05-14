'use strict';
angular.module('AfpcApp')
.directive('headerconfig', ['headerConfig', function (headerConfig){
	var thisController = ['$scope', '$timeout', '$http', '$location', '$window', '$modal', 
	                      function($scope, $timeout, $http, $location, $window, $modal){
		
		$http({
			method:'GET',
			url:'/api/cUserInfo'
		}).success(function(data){
			$scope.userInfo = data;
		}).error(function() {
			alert("error");
		}); 
		
		
	}];
	return {
		templateUrl: '/apps/commons/views/headerconfig.html',
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

