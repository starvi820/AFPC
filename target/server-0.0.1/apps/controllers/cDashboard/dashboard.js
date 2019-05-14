'use strict';
angular.module('AfpcApp')
	.controller('cDashbaord.DashboardCtrl', [ '$scope', '$http','$modal',
		function($scope, $http,$modal) {
		
	
		$scope.version = 1;
		
		$http({
			method : 'GET',
			url : '/api/cDashboard/hotfix_info'
		}).success(function(data) {
			
			$scope.hotfix_info = data;
			
			
		
			
			
		}).error(function(){
			alert(error);
		});
		

		
		$http({
			method : 'GET',
			url : '/api/cDashboard/getAcrobatInfo'
		}).success(function(data){
			$scope.acrobat_info = data;
			
//			var request = $http.post("/api/cDashboard/insertAppInfoAcrobatReader", $scope.acrobat_info);
//			request.then(function(regitm) {
//				console.log(regitm);
//				}, function(result) {
//					alert("error");
//				});
			
		}).error(function(){
			alert(error);
		});
		
		$http({
			method : 'GET',
			url : '/api/cDashboard/getFlashInfo'
		}).success(function (data){
			$scope.flash_info = data;
			
//			var request = $http.post("/api/cDashboard/insertAppInfoFlash", $scope.flash_info);
//			request.then(function(regitm) {
//				console.log(regitm);
//				}, function(result) {
//					alert("error");
//				});
			
			
		}).error(function (){
			alert("error");
		});
		
		
		
		
		$http({
			method : 'GET',
			url : '/api/cDashboard/getJavaInfo'
		}).success(function(data){
			$scope.java_info = data;
			
//			var request = $http.post("/api/cDashboard/insertAppInfoJava", $scope.java_info);
//			request.then(function(regitm) {
//				console.log(regitm);
//				}, function(result) {
//					alert("error");
//				});
			
		}).error(function (){
			alert("error");
		});
		
		$scope.versionview = function(type){
			
			$scope.version = type;
		}
		
		$scope.viewhotfix = function(itm){
			var modalInstance = $modal
			.open({
				animation : $scope.animationsEnabled,
				templateUrl : '/apps/views/cDashboard/modal/cHotfixDetailModal.html',
				controller : 'hotfixModalCtrl',
				size :'lg',
				resolve : {
					itm : function() {
						return {itm: itm};
					}
				}
			});
			modalInstance.result.then(function() {
				
			}, function() {
		
			});
		}
	}])
	.controller('hotfixModalCtrl', function($scope, $http, $modalInstance, itm) {
		$scope.retval = {};
		$scope.status = 1;
		var bulletin_id = itm.itm.bulletin_id;
		
		$http({
			method : 'GET',
			url : '/api/cDashboard/get_hotfix_id'
		}).success(function (itm){
			$scope.IDItems = itm;
		}).error(function(){
			alert("error");
		});
		
		$http({
			method : 'GET',
			url : '/api/cDashboard/hotfix_info_detail/' + bulletin_id
			
		}).success(function (itm){
			$scope.hotfix_info_detail= itm;
			
			
			
			
		}).error(function (){
			alert("데이터 불러오기 오류");
		});
		
		$http({
			method : 'GET',
			url : '/api/cDashboard/hotfix_info_detail/'+ bulletin_id
		}).success(function(data){
			$scope.hotfix_info_detail = data;
			
			
			
		}).error(function (){
			alert("error");
		});
		
		$scope.allview = function(){
			$http({
				method : 'GET',
				url : '/api/cDashboard/hotfix_info_detailAll'
			}).success(function (itm){
				$scope.hotfix_info_detail = itm;
				$scope.retval.selectedId = null;
				$scope.status = 2;
				
//				var request = $http.post("/api/cDashboard/insertAppInfoHotfix", $scope.hotfix_info_detail);
//				request.then(function(regitm) {
//					console.log(regitm);
//					}, function(result) {
//						alert("error");
//					});
				
			}).error(function(){
				alert("error");
			});
		}
		
		$scope.viewhotfix = function(id){
			$http({
				method : 'GET',
				url : '/api/cDashboard/hotfix_info_detail/'+ id
			}).success(function(data){
				$scope.hotfix_info_detail = data;
				$scope.status= 1;
			}).error(function (){
				alert("error");
			});
		}
		
		$scope.selectedId = function(bulletin_id){
			$scope.viewhotfix(bulletin_id);
			
			
		}
		
		$scope.cancel = function(){
			$modalInstance.close();
		}
		
	
	})
	;