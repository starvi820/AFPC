'use strict';
angular.module('AfpcApp')
.controller('cCheck.resultCtrl',[ '$scope', '$http', '$modal', '$location', 
						function($scope, $http, $modal, $location) {
	
	$scope.currentPage = 1;
	$scope.perPage = 10;
	$scope.searchItm = {};
	$scope.searchItm.type = 1;
	$scope.searchItm.status = 0;

	$scope.pageItems = [
	                    	{ name: '10', value: '10' },
	                    	{ name: '50', value: '50' },
	                    	{ name: '100', value: '100' }
	                    ];
	
	$scope.pageItem = $scope.pageItems[0];
	
	updateAll();
	
	function updateAll(){
		$http({
			method : 'GET',
			url : '/api/cCheck/getResult/'+$scope.currentPage+'/'+$scope.pageItem.value
		}).success(function(data){
			$scope.itms = data.result;
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
		}).error(function(){
			
		});
	}
	
	function searchCheckResult(){
		$http({
			method : 'GET',
			url : '/api/cCheck/searchResult/' + $scope.searchItm.type + "/" + $scope.searchItm.text + "/" + $scope.currentPage + "/" + $scope.pageItem.value
		}).success(function(data) {
			
			$scope.itms = data.result;
			
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
			$scope.searchItm.status = 1;
			
		}).error(function() {
			
		});
	}
	
	$scope.search = function(){
		if($scope.searchItm.text == null || $scope.searchItm.text == "" || $scope.searchItm.text == undefined){
			alert("검색어를 입력하세요.");
			return;
		}
		searchCheckResult();
	}
	
	$scope.pageReload = function(){
		$http({
			method : 'GET',
			url : '/api/cCheck/getResult/'+$scope.currentPage+'/'+$scope.pageItem.value
		}).success(function(data){
			$scope.currentPage = 1;
			$scope.perPage = 10;
			$scope.searchItm = {};
			$scope.searchItm.type = 1;
			$scope.searchItm.status = 0;
			
			$scope.itms = data.result;
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
		}).error(function(){
			
		});
	}
	
	
}])
;