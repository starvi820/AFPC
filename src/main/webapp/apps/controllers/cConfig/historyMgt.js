'use strict';
angular.module('AfpcApp')
.controller('cConfig.historyMgtCtrl',[ '$scope', '$http', '$modal', '$location',
						function($scope, $http, $modal, $location) {
	
	$scope.searchLoginItm = {};
	$scope.searchLoginItm.type = 1;
	$scope.searchLoginItm.text = "";
	$scope.searchLoginItm.status = 0;
	
	$scope.searchPageItm = {};
	$scope.searchPageItm.type = 1;
	$scope.searchPageItm.text = "";
	$scope.searchPageItm.status = 0;
	
	$scope.loginHistItm = {};
	$scope.loginHistItm.currentPage = 1;
	$scope.loginHistItm.perPage = 10;
	
	$scope.pageHistItm = {};
	$scope.pageHistItm.currentPage = 1;
	$scope.pageHistItm.perPage = 10;
	
	updateLoginHistory();
	updatePageHistory();
	
	function updateLoginHistory(){
		$http({
			method : 'GET',
			url : '/api/cHistoryMgt/getLoginHistory/'+$scope.loginHistItm.currentPage+'/10'
		}).success(function(data){
			
			$scope.loginItms = data.result;
			
			$scope.loginHistItm.totalItems = data.totalArticle;
			$scope.loginHistItm.currentPage = data.currentPage;
			$scope.loginHistItm.perPage = data.perArticle;
			$scope.loginHistItm.startNo = data.startArticleNo;
			
		}).error(function(){
			
		});
	}
	
	function updatePageHistory(){
		$http({
			method : 'GET',
			url : '/api/cHistoryMgt/getPageHistory/'+$scope.pageHistItm.currentPage+'/10'
		}).success(function(data){
			
			$scope.pageItms = data.result;
			
			$scope.pageHistItm.totalItems = data.totalArticle;
			$scope.pageHistItm.currentPage = data.currentPage;
			$scope.pageHistItm.perPage = data.perArticle;
			$scope.pageHistItm.startNo = data.startArticleNo;
			
		}).error(function(){
			
		});
	}
	
	function searchLoginHistory(){
		$http({
			method : 'GET',
			url : '/api/cHistoryMgt/searchLoginHistory/' + $scope.searchLoginItm.type + "/" + $scope.searchLoginItm.text + "/" + $scope.loginHistItm.currentPage + "/10"
		}).success(function(data) {
			
			$scope.loginItms = data.result;
			
			$scope.loginHistItm.totalItems = data.totalArticle;
			$scope.loginHistItm.currentPage = data.currentPage;
			$scope.loginHistItm.perPage = data.perArticle;
			$scope.loginHistItm.startNo = data.startArticleNo;
			
			$scope.searchLoginItm.status = 1;
			
		}).error(function() {
		});
	}
	
	function searchPageHistory(){
		$http({
			method : 'GET',
			url : '/api/cHistoryMgt/searchPageHistory/' + $scope.searchPageItm.type + "/" + $scope.searchPageItm.text + "/" + $scope.pageHistItm.currentPage + "/10"
		}).success(function(data) {
			
			$scope.pageItms = data.result;
			
			$scope.pageHistItm.totalItems = data.totalArticle;
			$scope.pageHistItm.currentPage = data.currentPage;
			$scope.pageHistItm.perPage = data.perArticle;
			$scope.pageHistItm.startNo = data.startArticleNo;
			
			$scope.searchPageItm.status = 1;
			
		}).error(function() {
		});
	}
	
	$scope.searchLogin = function(){
		
		if($scope.searchLoginItm.text == null || $scope.searchLoginItm.text == "" || $scope.searchLoginItm.text == undefined){
			alert("검색어를 입력하세요.");
			return;
		}
		searchLoginHistory();
	}
		
	
	$scope.searchPage = function(){
		
		if($scope.searchPageItm.text == null || $scope.searchPageItm.text == "" || $scope.searchPageItm.text == undefined){
			alert("검색어를 입력하세요.");
			return;
		}
		searchPageHistory();
		
	}
	
	$scope.reloadLoginHistory = function(){
		$http({
			method : 'GET',
			url : '/api/cHistoryMgt/getLoginHistory/'+$scope.loginHistItm.currentPage+'/10'
		}).success(function(data){
			
			$scope.loginItms = data.result;
			
			$scope.loginHistItm.totalItems = data.totalArticle;
			$scope.loginHistItm.currentPage = data.currentPage;
			$scope.loginHistItm.perPage = data.perArticle;
			$scope.loginHistItm.startNo = data.startArticleNo;
			
			$scope.searchLoginItm = {};
			$scope.searchLoginItm.type = 1;
			$scope.searchLoginItm.text = "";
			$scope.searchLoginItm.status = 0;
			
		}).error(function(){
			
		});
	}
	
	$scope.reloadPageHistory = function(){
		$http({
			method : 'GET',
			url : '/api/cHistoryMgt/getPageHistory/'+$scope.pageHistItm.currentPage+'/10'
		}).success(function(data){
			
			$scope.pageItms = data.result;
			
			$scope.pageHistItm.totalItems = data.totalArticle;
			$scope.pageHistItm.currentPage = data.currentPage;
			$scope.pageHistItm.perPage = data.perArticle;
			$scope.pageHistItm.startNo = data.startArticleNo;
			
			$scope.searchPageItm = {};
			$scope.searchPageItm.type = 1;
			$scope.searchPageItm.text = "";
			$scope.searchPageItm.status = 0;
			
		}).error(function(){
			
		});
	}
	
	$scope.loginPageChanged = function(){
		if($scope.searchLoginItm.status == 0){
			updateLoginHistory();
		}else if($scope.searchLoginItm.status == 1){
			searchLoginHistory();
		}
		
	}
	
	$scope.pagePageChanged = function(){
		if($scope.searchPageItm.status == 0){
			updatePageHistory();
		}else if($scope.searchPageItm.status == 1){
			searchPageHistory();
		}
	}

}])
;