'use strict';
angular.module('AfpcApp')
.factory('cInfoService',['$http', function($http) {
	var item = {};
	return {
		getSeq : function() {
			return this.seq;
		},
		setSeq : function(i) {
			this.seq = i;
		},
		getStatus : function() {
			return this.status;
		},
		setStatus : function(i) {
			this.status = i;
		},
		getTargetItm : function() {
			return this.targetItm;
		},
		setTargetItm : function(i) {
			this.targetItm = i;
		}
	}
}])
.controller('cInfo.infoCtrl',[ '$scope', '$http', '$modal', '$location', 'cInfoService', 
						function($scope, $http, $modal, $location, cInfoService) {
	
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
			url : '/api/cInfo/getList/'+$scope.currentPage+'/'+$scope.pageItem.value
		}).success(function(data){
			
			$scope.itms = data.result;
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
			
		}).error(function(){
			
		});
	}
	
	function searchInfo(){
		$http({
			method : 'GET',
			url : '/api/cInfo/searchInfo/' + $scope.searchItm.type + "/" + $scope.searchItm.text + "/" + $scope.currentPage + "/" + $scope.pageItem.value
		}).success(function(data) {
			
			$scope.itms = data.result;
			console.log($scope.itms);
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
		searchInfo();
	}
	
	$scope.pageChanged = function() {
		if($scope.searchItm.status == 0){
			updateAll();
		}else{
			searchInfo();
		}
	}
	
	$scope.pageReload = function(){
		$http({
			method : 'GET',
			url : '/api/cInfo/getList/'+$scope.currentPage+'/'+$scope.pageItem.value
		}).success(function(data){
			
			$scope.currentPage = 1;
			$scope.perPage = 10;
			
			$scope.searchItm = {};
			$scope.searchItm.status = 0;
			$scope.searchItm.type = 1;
			$scope.searchItm.text = "";
			
			$scope.itms = data.result;
			
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
			
		}).error(function(){
			
		});
	}

	$scope.viewInfo = function(seq){
		for(var i=0;i<$scope.itms.length;i++){
			if($scope.itms[i].seq == seq){
				cInfoService.setSeq(seq);
				cInfoService.setTargetItm($scope.itms[i]);
				cInfoService.setStatus(1);
				$location.path('/base/cInfoView');
			}
		}
	}
	
	$scope.rgtInfo = function(){
		cInfoService.setStatus(3);
		$location.path('/base/cInfoView');
	}
	
}])
.controller('cInfo.cInfoViewCtrl',[ '$scope', '$http', '$location', '$window', '$modal', 'cInfoService',
		function($scope, $http, $location, $window, $modal, cInfoService ) {
	
	if(cInfoService.getStatus() == undefined){
		window.history.back();
	}
	
	console.log(cInfoService.getStatus());
	
	var historyItm = {};
	historyItm.accessTable = "af_info";
	historyItm.accessMenu = "알림";
	
	$scope.status = cInfoService.getStatus();
	
	if($scope.status == 3){
		$scope.itm = {};
	}
	
	if($scope.status == 1){
		if(cInfoService.getSeq() == undefined){
			window.history.back();
		}
		
		$scope.itm = cInfoService.getTargetItm();
		
		$scope.edtInfo = function(){
			
			if($scope.status == 1){
				$scope.newItms = {};
				$scope.newItms.title = $scope.itm.title;
				$scope.newItms.content = $scope.itm.content;
				
				$scope.status = 2;
			}else if($scope.status == 2){
				
				if($scope.itm.title == null || $scope.itm.title.length == 0 || $scope.itm.title == undefined){
					alert("제목을 입력해 주세요.");
					return;
				}
				
				var c = confirm("수정하시겠습니까?");
				if(c == true){
				
					var request = $http.post("/api/cInfo/updateInfo", $scope.itm);
					request.then(function(edtitm) {
						
						historyItm.action = "U";
						historyItm.accessId = $scope.itm.title;
						
						$http.post('/api/insertPageHistory', historyItm)
					    .then( function(resp) {
							
					    	alert("수정되었습니다.");
							$scope.status = 1;
							
							})
						}, function(result) {
							if (result.data.code != 10)
								alert(result.data.message);
							else
								alert(result.data.message);
					});
				}else{
					return;
				}
			}
		}
		
		$scope.delInfo = function(){
			var c = confirm("삭제하시겠습니까?");
			if(c == true){
			
				var request = $http.post("/api/cInfo/deleteInfo/"+$scope.itm.seq);
				request.then(function(edtitm) {
					
					historyItm.action = "D";
					historyItm.accessId = $scope.itm.title;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
						
				    	alert("삭제되었습니다.");
				    	$location.path('/base/cInfo');
						
						})
					}, function(result) {
						if (result.data.code != 10)
							alert(result.data.message);
						else
							alert(result.data.message);
				});
			}else{
				return;
			}
		}
	}
	
	$scope.goBack = function(){
		if($scope.status != 2){
			window.history.back();
		}else if($scope.status == 2){
			
			if($scope.newItms.title != $scope.itm.title){
				$scope.itm.title = $scope.newItms.title;
			}
			if($scope.newItms.content != $scope.itm.content){
				$scope.itm.content = $scope.newItms.content;
			}
			
			$scope.status = 1;
		}
	}
	
	$scope.regInfo = function(){
		
		if($scope.itm.title == null || $scope.itm.title.length == 0 || $scope.itm.title == undefined){
			alert("제목을 입력해 주세요.");
			return;
		}
		
		var c = confirm("등록하시겠습니까?");
		if(c == true){
		
			var request = $http.post("/api/cInfo/regitInfo", $scope.itm);
			request.then(function(regitm) {
				
				historyItm.action = "I";
				historyItm.accessId = $scope.itm.title;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
					
			    	alert("등록되었습니다.");
					$location.path('/base/cInfo');
					
					})
				}, function(result) {
					if (result.data.code != 10)
						alert(result.data.message);
					else
						alert(result.data.message);
				});
		}else{
			return;
		}
	}
	
}])
;
