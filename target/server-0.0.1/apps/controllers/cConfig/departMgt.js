'use strict';
angular.module('AfpcApp')
.controller('cConfig.departMgtCtrl',[ '$scope', '$http', '$modal', '$location',
						function($scope, $http, $modal, $location) {
	
	$scope.companyList = [];
	$scope.departList = [];
	$scope.teamList = [];
	
	$scope.selectedCompanyItm = null;
	$scope.selectedDepartItm = null;
	$scope.selectedTeamItm = null;
	
	var historyItm = {};
	historyItm.accessMenu = "환경설정/부서관리";
	
	getCompany();
	
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
			console.log(data);
			$scope.teamList = data;
			
		}).error(function(){
			alert(SECONE_ERROR);
		})
	}
	
	$scope.selectTeam = function(itm){
		$scope.selectedTeamItm = itm;
		
	}
	
	
	//회사 등록
	$scope.regCompany = function(){
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/departMgt/compnayModal.html',
			controller : 'companyModalCtrl',
			resolve : {
				itm : function() {
					return {status : 1};
				}
			}
		});
		modalInstance.result.then(function(item) {
			getCompany();
		}, function() {
	
		});
		
	}
	
	//회사수정
	$scope.editCompany = function(){
		
		var cItm = $scope.selectedCompanyItm;
		
		if(cItm == null){
			alert("회사를 선택해 주세요.");
			return;
		}
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/departMgt/compnayModal.html',
			controller : 'companyModalCtrl',
			resolve : {
				itm : function() {
					return {status : 2, data: cItm};
				}
			}
		});
		modalInstance.result.then(function(item) {
			
		}, function() {
	
		});
	}
	
	//회사삭제
	$scope.delCompany = function(){
		
		var cItm = $scope.selectedCompanyItm;
		if(cItm == null){
			alert("회사를 선택해 주세요.");
			return;
		}
		
		var c = confirm("삭제하시겠습니까?");
		if(c == true){
			var request = $http.post("/api/cDepartMgt/deleteCompany", cItm);
			request.then(function(regitm) {
				
				historyItm.action = "D";
				historyItm.accessTable = "af_company"
				historyItm.accessId = cItm.name;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
			    	alert("삭제되었습니다.");
					getCompany();
			    }, function(resp){
			    	
			    }); 
				
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
	
	//부서등록
	$scope.regDepart = function(){
		
		var cItm = $scope.selectedCompanyItm;
		if(cItm == null){
			alert("회사를 선택해 주세요.");
			return;
		}
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/departMgt/departModal.html',
			controller : 'departModalCtrl',
			resolve : {
				itm : function() {
					return {status : 1, cItm: cItm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			$scope.departList = [];
			$scope.teamList = [];
			$http({
				method : 'GET',
				url : '/api/cDepartMgt/getDepart/'+itm.data.af_company_seq
			}).success(function(data){
				$scope.departList = data;
				
			}).error(function(){
				alert(SECONE_ERROR);
			})
		}, function() {
	
		});
		
		
	}
	
	//부서수정
	$scope.editDepart = function(){
		
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
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/departMgt/departModal.html',
			controller : 'departModalCtrl',
			resolve : {
				itm : function() {
					return {status : 2, cItm: cItm, dItm: dItm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			$scope.departList = [];
			$scope.teamList = [];
			$http({
				method : 'GET',
				url : '/api/cDepartMgt/getDepart/'+itm.data.af_company_seq
			}).success(function(data){
				$scope.departList = data;
				
			}).error(function(){
				alert(SECONE_ERROR);
			})
			
		}, function() {
	
		});
		
	}
	
	//부서삭제
	$scope.delDepart = function(){
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
		
		var c = confirm("삭제하시겠습니까?");
		if(c == true){
			var request = $http.post("/api/cDepartMgt/deleteDepart", dItm);
			request.then(function(regitm) {
				
				historyItm.action = "D";
				historyItm.accessTable = "af_department"
				historyItm.accessId = dItm.name;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
			    	alert("삭제되었습니다.");
					
					$scope.departList = [];
					$scope.teamList = [];
					$http({
						method : 'GET',
						url : '/api/cDepartMgt/getDepart/'+dItm.af_company_seq
					}).success(function(data){
						$scope.departList = data;
						
					}).error(function(){
						alert(SECONE_ERROR);
					})
			    }, function(resp){
			    	
			    }); 
				
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
	
	//팀 등록
	$scope.regTeam = function(){
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
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/departMgt/teamModal.html',
			controller : 'teamModalCtrl',
			resolve : {
				itm : function() {
					return {status : 1, cItm: cItm, dItm: dItm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			
			$scope.teamList = [];
			$http({
				method : 'GET',
				url : '/api/cDepartMgt/getTeam/'+itm.data.af_company_seq+'/'+itm.data.af_department_seq
			}).success(function(data){
				console.log(data);
				$scope.teamList = data;
				
			}).error(function(){
				alert(SECONE_ERROR);
			})
		}, function() {
	
		});
	}
	
	//팀 수정
	$scope.editTeam = function(){
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
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/departMgt/teamModal.html',
			controller : 'teamModalCtrl',
			resolve : {
				itm : function() {
					return {status : 2, cItm: cItm, dItm: dItm, tItm: tItm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			$scope.teamList = [];
			$http({
				method : 'GET',
				url : '/api/cDepartMgt/getTeam/'+itm.data.af_company_seq+'/'+itm.data.af_department_seq
			}).success(function(data){
				console.log(data);
				$scope.teamList = data;
				
			}).error(function(){
				alert(SECONE_ERROR);
			})
			
		}, function() {
	
		});
		
		
	}
	
	//팀 삭제
	$scope.delTeam = function(){
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
		var c = confirm("삭제하시겠습니까?");
		if(c == true){
			var request = $http.post("/api/cDepartMgt/deleteTeam", tItm);
			request.then(function(regitm) {
				
				historyItm.action = "D";
				historyItm.accessTable = "af_team"
				historyItm.accessId = tItm.name;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
			    	alert("삭제되었습니다.");
					
					$scope.teamList = [];
					$http({
						method : 'GET',
						url : '/api/cDepartMgt/getTeam/'+cItm.seq+'/'+dItm.seq
					}).success(function(data){
						console.log(data);
						$scope.teamList = data;
						
					}).error(function(){
						alert(SECONE_ERROR);
					})
			    }, function(resp){
			    	
			    }); 
								
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
.controller('companyModalCtrl', function($scope, $http, $modalInstance, itm) {
	
	$scope.status = itm.status;
	
	var historyItm = {};
	historyItm.accessMenu = "환경설정/부서관리";
	
	if($scope.status == 1){
		$scope.itm = {};
	}else{
		$scope.itm = itm.data;
		
		$scope.newItm = {};
		$scope.newItm.name = $scope.itm.name;
	}
	
	
	$scope.cancle = function(){
		if($scope.status == 2){
			if($scope.newItm.name != $scope.itm.name){
				$scope.itm.name = $scope.newItm.name;
			}
		}
		$modalInstance.dismiss('cancle');
	}
	
	$scope.regCompany = function(){
		
		if($scope.itm.name == null || $scope.itm.name == "" || $scope.itm.name == undefined){
			alert("회사명을 입력해 주세요.");
			return;
		}
		
		if($scope.status == 1){
			
			var c = confirm("등록하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cDepartMgt/insertCompany", $scope.itm);
				request.then(function(regitm) {
					
					historyItm.action = "I";
					historyItm.accessTable = "af_company"
					historyItm.accessId = $scope.itm.name;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("등록되었습니다.");
						$modalInstance.close(regitm);
				    }, function(resp){
				    	
				    }); 
					
				}, function(result) {
					if (result.data.code != 10)
						alert(result.data.message);
					else
						alert(result.data.message);
				});
			}else{
				return;
			}
			
		}else if($scope.status == 2){
			
			var c = confirm("수정하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cDepartMgt/updateCompany", $scope.itm);
				request.then(function(edititm) {
					
					historyItm.action = "U";
					historyItm.accessTable = "af_company"
					historyItm.accessId = $scope.itm.name;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("수정되었습니다.");
						$modalInstance.close(edititm);
				    }, function(resp){
				    	
				    }); 
					
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
	
})
.controller('departModalCtrl', function($scope, $http, $modalInstance, itm) {
	
	$scope.status = itm.status;
	
	var historyItm = {};
	historyItm.accessMenu = "환경설정/부서관리";
	
	if($scope.status == 1){
		$scope.itm = {};
		$scope.cItm = itm.cItm;
	}else{
		$scope.itm = itm.dItm;
		$scope.cItm = itm.cItm;
		
		$scope.newItm = {};
		$scope.newItm.name = $scope.itm.name;
	}
	
	
	$scope.cancle = function(){
		if($scope.status == 2){
			if($scope.newItm.name != $scope.itm.name){
				$scope.itm.name = $scope.newItm.name;
			}
		}
		$modalInstance.dismiss('cancle');
	}
	
	$scope.regDepart = function(){
		
		if($scope.itm.name == null || $scope.itm.name == "" || $scope.itm.name == undefined){
			alert("부서명을 입력해 주세요.");
			return;
		}
		
		if($scope.status == 1){
			
			$scope.itm.af_company_seq = $scope.cItm.seq;
			
			var c = confirm("등록하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cDepartMgt/insertDepart", $scope.itm);
				request.then(function(regitm) {
					
					historyItm.action = "I";
					historyItm.accessTable = "af_department"
					historyItm.accessId = $scope.itm.name;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("등록되었습니다.");
						regitm.data = $scope.itm;
						$modalInstance.close(regitm);
				    }, function(resp){
				    	
				    }); 
					
				}, function(result) {
					if (result.data.code != 10)
						alert(result.data.message);
					else
						alert(result.data.message);
				});
			}else{
				return;
			}
			
		}else if($scope.status == 2){
			
			var c = confirm("수정하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cDepartMgt/updateDepart", $scope.itm);
				request.then(function(edititm) {
					
					historyItm.action = "U";
					historyItm.accessTable = "af_department"
					historyItm.accessId = $scope.itm.name;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("수정되었습니다.");
						edititm.data = $scope.itm;
						$modalInstance.close(edititm);
				    }, function(resp){
				    	
				    }); 
					
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
	
})
.controller('teamModalCtrl', function($scope, $http, $modalInstance, itm) {
	
	$scope.status = itm.status;
	
	var historyItm = {};
	historyItm.accessMenu = "환경설정/부서관리";
	
	if($scope.status == 1){
		$scope.itm = {};
		$scope.cItm = itm.cItm;
		$scope.dItm = itm.dItm;
	}else{
		$scope.itm = itm.tItm;
		$scope.cItm = itm.cItm;
		$scope.dItm = itm.dItm;
		
		$scope.newItm = {};
		$scope.newItm.name = $scope.itm.name;
	}
	
	
	$scope.cancle = function(){
		if($scope.status == 2){
			if($scope.newItm.name != $scope.itm.name){
				$scope.itm.name = $scope.newItm.name;
			}
		}
		$modalInstance.dismiss('cancle');
	}
	
	$scope.regTeam = function(){
		
		if($scope.itm.name == null || $scope.itm.name == "" || $scope.itm.name == undefined){
			alert("팀명을 입력해 주세요.");
			return;
		}
		
		if($scope.status == 1){
			
			$scope.itm.af_company_seq = $scope.cItm.seq;
			$scope.itm.af_department_seq = $scope.dItm.seq;
			
			var c = confirm("등록하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cDepartMgt/insertTeam", $scope.itm);
				request.then(function(regitm) {
					
					historyItm.action = "I";
					historyItm.accessTable = "af_team"
					historyItm.accessId = $scope.itm.name;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("등록되었습니다.");
						regitm.data = $scope.itm;
						$modalInstance.close(regitm);
				    }, function(resp){
				    	
				    }); 
					
				}, function(result) {
					if (result.data.code != 10)
						alert(result.data.message);
					else
						alert(result.data.message);
				});
			}else{
				return;
			}
			
		}else if($scope.status == 2){
			
			var c = confirm("수정하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cDepartMgt/updateTeam", $scope.itm);
				request.then(function(edititm) {
					
					historyItm.action = "U";
					historyItm.accessTable = "af_team"
					historyItm.accessId = $scope.itm.name;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("수정되었습니다.");
						edititm.data = $scope.itm;
						$modalInstance.close(edititm);
				    }, function(resp){
				    	
				    }); 
					
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
	
})
;