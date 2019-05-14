'use strict';
angular.module('AfpcApp')
.controller('cConfig.patchMgtCtrl',[ '$scope', '$http', '$modal', '$location',
						function($scope, $http, $modal, $location) {
	
	$scope.location = $location.absUrl();
	console.log($scope.location);
	
}])
;