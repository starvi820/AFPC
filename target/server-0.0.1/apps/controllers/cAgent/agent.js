'use strict';
angular.module('AfpcApp')
.controller('cAgent.agentCtrl',[ '$scope', '$http', '$modal', '$location', 
						function($scope, $http, $modal, $location) {
	
	$scope.currentPage = 1;
	$scope.perPage = 10;
	
	$scope.searchItm = {};
	$scope.searchItm.type = 1;
	$scope.searchItm.text = "";
	$scope.searchItm.status = 0;
	
	$scope.pageItems = [
	                    	{ name: '10', value: '10' },
	                    	{ name: '20', value: '20' },
	                    	{ name: '50', value: '50' }
	                    ];
	
	$scope.pageItem = $scope.pageItems[0];
	
	$scope.search = function(){
		if($scope.searchItm.text == null || $scope.searchItm.text == ""){
			alert("검색어를 입력하세요.");
			return;
		}
		searchAgent();
	}
	
	updateAll();
	
	function updateAll(){
		$http({
			method : 'GET',
			url : '/api/cAgent/getAgent/'+$scope.currentPage+'/'+$scope.pageItem.value
		}).success(function(data){
			
			$scope.itms = data.result;
			
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
			
		}).error(function(){
			
		});
	}
	
	function searchAgent(){
		$http({
			method : 'GET',
			url : '/api/cAgent/searchAgent/' + $scope.searchItm.type + "/" + $scope.searchItm.text + "/" + $scope.currentPage + "/" + $scope.pageItem.value
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
	
	$scope.pageChanged = function() {
		if($scope.searchItm.status == 0){
			updateAll();
		}else if($scope.searchItm.status == 1){
			searchAgent();
		}
	}
	
	$scope.pageReload = function(){
		
		$http({
			method : 'GET',
			url : '/api/cAgent/getAgent/'+$scope.currentPage+'/'+$scope.pageItem.value
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


	$scope.viewAgent = function(seq){
		for(var i=0;i<$scope.itms.length;i++){
			if($scope.itms[i].seq == seq){
				var aItm = $scope.itms[i];
				
			}
		}
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cAgent/modal/agentModal.html',
			controller : 'agentModalCtrl',
			size : 'lg',
			resolve : {
				itm : function() {
					return {status : 1, itm : aItm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			if($scope.searchItm.status == 0){
				updateAll();
			}
		}, function() {
	
		});
	}
	
	$scope.rgtAgent = function(){
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cAgent/modal/agentModal.html',
			controller : 'agentModalCtrl',
			size : 'lg',
			resolve : {
				itm : function() {
					return {status : 3};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			updateAll();
		}, function() {
	
		});
	}
	
	$scope.infoRgt = function(){
		cInfoService.setStatus(3);
		$location.path('/base/cInfoView');
	}
	
	
	$scope.rgtExcel = function(){
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cAgent/modal/regitExcelModal.html',
			controller : 'regitExcelModalCtrl',
			size : 'lg',
			resolve : {
				itm : function() {
					return {status : 3};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			updateAll();
		}, function() {

		});
	}
	
}])
.controller('regitExcelModalCtrl', function($scope, $http, $modalInstance, itm, Upload, $location) {
	
	$scope.status = 0;
	$scope.attachitms = [];
	
	$scope.rmfile = function(idx) {
		var f = $scope.attachitms[idx];
		$scope.attachitms.splice(idx, 1);
	}
	
	$scope.onFileSelect = function(files) {
		var fnum = 0;
		if (angular.isArray(files)) {
			for (var i = 0; i < files.length; i++) {
				if ($scope.attachitms.length + fnum >= 1) {
					alert("하나의 첨부파일을 등록해주세요.");
					return;
				}
				$scope.attachitms.push(files[i]);
			}
		} else {
			if (angular.isDefined(files)) {
				$scope.attachitms.push(files);
			}
		}
	}
	
	$scope.regExcel = function(){
		if($scope.attachitms.length == 0){
			alert("첨부파일을 등록해주세요.");
			return;
		}
		
		var c = confirm("등록하시겠습니까?");
		if(c == true){
		
			var up = Upload.upload({
				url : '/api/cAgent/regitAgentExcel',
				method : 'POST',
				file : $scope.attachitms,
				fileFormDataName : 'excelfile'
			});
			up.then(function(response) {
				var result = angular.fromJson(response.data);
				if (result.code == 10) {
					
					$scope.itms = result.data;
					$scope.status = 1;
					
				} else {
					alert(result.message);
				}
			}, function(response) {
				if (response.status == 400
						&& response.data.code == -20) {
					alert(response.data.message);
					return;
				}
				if (response.status > 0)
					alert(response.status + ' : ' + "파일 등록을 실패하였습니다.");
			});
			
		}else{
			return;
		}
	}
	
	$scope.cancle = function(){
		$modalInstance.close();
	}
	
})
.controller('agentModalCtrl', function($scope, $http, $modalInstance, itm) {
	
	var historyItm = {};
	historyItm.accessTable = "af_agent";
	historyItm.accessMenu = "점검대상관리/Agent관리";
	
	$scope.status = itm.status;
	
	if($scope.status != 3){
		$scope.itm = itm.itm;
	}else if($scope.status == 3){
		$scope.itm = {};
		
		getCompany();
		
	}
	
	$scope.editAgent = function(){
		if($scope.status == 1){
			$scope.newItm = {};
			$scope.newItm.agentIp = $scope.itm.agentIp;
			$scope.newItm.agentUser = $scope.itm.agentUser;
			$scope.newItm.agentEmail = $scope.itm.agentEmail;
			
			getCompany();
			$scope.status = 2;
		}else if($scope.status == 2){
			
			if($scope.itm.agentUser == null || $scope.itm.agentUser == "" || $scope.itm.agentUser == undefined){
				alert("Agent User를 입력해주세요.");
				return;
			}
			if($scope.itm.agentEmail == null || $scope.itm.agentEmail == "" || $scope.itm.agentEmail == undefined){
				alert("User Email을 입력해주세요.");
				return;
			}
			if($scope.itm.agentIp == null || $scope.itm.agentIp == "" || $scope.itm.agentIp == undefined){
				alert("Agent IP를 입력해주세요.");
				return;
			}
			var cItm = $scope.selectedCompanyItm;
			if(cItm == null){
				alert("회사를 선택해 주세요.");
				return;
			}
			var dItm = $scope.selectedDepartItm;
			if(dItm == null){
				alert("부서를 선택해 주세요.");
				return;
			}
			var tItm = $scope.selectedTeamItm;
			if(tItm == null){
				alert("팀을 선택해 주세요.");
				return;
			}
			
			$scope.itm.companySeq = cItm.seq;
			$scope.itm.departSeq = dItm.seq;
			$scope.itm.teamSeq = tItm.seq;
			
			var c = confirm("수정하시겠습니까?");
			if(c == true){
			
				var request = $http.post("/api/cAgent/updateAgent", $scope.itm);
				request.then(function(regitm) {
					
					historyItm.action = "U";
					historyItm.accessId = $scope.itm.agentId;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
						
				    	alert("수정되었습니다.");
				    	
				    	$http({
							method : 'GET',
							url : '/api/cAgent/getAgent/'+$scope.itm.seq
						}).success(function(data){
							$scope.itm = data;
							$scope.status = 1;
						}).error(function(){
							
						});
						
						
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
	
	$scope.cancle = function(){
		if($scope.status == 2){
			if($scope.newItm.agentIp != $scope.itm.agentIp){
				$scope.itm.agentIp = $scope.newItm.agentIp;
			}
			if($scope.newItm.agentUser != $scope.itm.agentUser){
				$scope.itm.agentUser = $scope.newItm.agentUser;
			}
			if($scope.newItm.agentEmail != $scope.itm.agentEmail){
				$scope.itm.agentEmail = $scope.newItm.agentEmail;
			}
			
			$scope.status = 1;
		}else{
			$modalInstance.close();
		}
	}
	
	$scope.regAgent = function(){
		
		if($scope.itm.agentUser == null || $scope.itm.agentUser == "" || $scope.itm.agentUser == undefined){
			alert("Agent User를 입력해주세요.");
			return;
		}
		if($scope.itm.agentEmail == null || $scope.itm.agentEmail == "" || $scope.itm.agentEmail == undefined){
			alert("User Email을 입력해주세요.");
			return;
		}
		if($scope.itm.agentIp == null || $scope.itm.agentIp == "" || $scope.itm.agentIp == undefined){
			alert("Agent IP를 입력해주세요.");
			return;
		}
		var cItm = $scope.selectedCompanyItm;
		if(cItm == null){
			alert("회사를 선택해 주세요.");
			return;
		}
		var dItm = $scope.selectedDepartItm;
		if(dItm == null){
			alert("부서를 선택해 주세요.");
			return;
		}
		var tItm = $scope.selectedTeamItm;
		if(tItm == null){
			alert("팀을 선택해 주세요.");
			return;
		}
		
		$scope.itm.companySeq = cItm.seq;
		$scope.itm.departSeq = dItm.seq;
		$scope.itm.teamSeq = tItm.seq;
		
		var c = confirm("등록하시겠습니까?");
		if(c == true){
		
			var request = $http.post("/api/cAgent/regitAgent", $scope.itm);
			request.then(function(regitm) {
				
				historyItm.action = "I";
				historyItm.accessId = $scope.itm.agentId;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
					
			    	alert("등록되었습니다.");
					$modalInstance.close(regitm);
					
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
	
	$scope.delAgent = function(){
		var c = confirm("삭제하시겠습니까?");
		if(c == true){
		
			var request = $http.post("/api/cAgent/deleteAgent/"+$scope.itm.seq);
			request.then(function(regitm) {
				
				historyItm.action = "D";
				historyItm.accessId = $scope.itm.agentId;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
					
			    	alert("삭제되었습니다.");
					$modalInstance.close();
					
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
	
	function getCompany(){
		
		$scope.companyList = [];
		$scope.departList = [];
		$scope.teamList = [];
		
		$scope.selectedCompanyItm = null;
		$scope.selectedDepartItm = null;
		$scope.selectedTeamItm = null;
		
		$http({
			method : 'GET',
			url : '/api/cDepartMgt/getCompany'
		}).success(function(data){
			$scope.companyList = data;
			
		}).error(function(){
			alert(SECONE_ERROR);
		})
	}
	
	$scope.selectCompany = function(itm){
		
		$scope.selectedCompanyItm = itm;
		
		$scope.departList = [];
		$scope.teamList = [];
		$http({
			method : 'GET',
			url : '/api/cDepartMgt/getDepart/'+itm.seq
		}).success(function(data){
			$scope.departList = data;
			
		}).error(function(){
			alert(SECONE_ERROR);
		})
	}
	
	$scope.selectDepart = function(itm){
		
		$scope.selectedDepartItm = itm;
		
		$scope.teamList = [];
		$http({
			method : 'GET',
			url : '/api/cDepartMgt/getTeam/'+itm.af_company_seq+'/'+itm.seq
		}).success(function(data){
			$scope.teamList = data;
		}).error(function(){
			alert(SECONE_ERROR);
		})
	}
	
	$scope.selectTeam = function(itm){
		$scope.selectedTeamItm = itm;
		
	}
	
	

	
	
})
;