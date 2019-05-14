'use strict';
angular.module('AfpcApp')
.controller('cConfig.adminMgtCtrl',[ '$scope', '$http', '$modal', '$location',
						function($scope, $http, $modal, $location) {
	
	$scope.currentPage = 1;
	$scope.perPage = 10;
	
	$scope.searchItm = {};
	$scope.searchItm.status = 0;
	$scope.searchItm.type = 1;
	$scope.searchItm.text = "";
	
	$scope.pageItems = [
	                    	{ name: '10', value: '10' },
	                    	{ name: '20', value: '20' },
	                    	{ name: '50', value: '50' }
	                    ];
	
	$scope.pageItem = $scope.pageItems[0];
	
	function searchAdmin(){
		if($scope.searchItm.text == null || $scope.searchItm.text == "" || $scope.searchItm.text == undefined){
			alert("검색어를 입력하세요.");
			return;
		}
		
		$http({
			method : 'GET',
			url : '/api/cAdminMgt/searchAdmin/' + $scope.searchItm.type + "/" + $scope.searchItm.text + "/" + $scope.currentPage + "/" + $scope.pageItem.value
		}).success(function(data) {
			
			$scope.itms = data.result;
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.startNo = data.startArticleNo;
			$scope.searchItm.status = 1;
			
		}).error(function() {
		});
	}
	
	$scope.search = function(){
		searchAdmin();
	}
	
	$scope.pageChanged = function() {
		if($scope.searchItm.status == 0){
			updateAll();
		}else{
			searchAdmin();
		}
	}
	
	$scope.pageReload = function(){
		$http({
			method : 'GET',
			url : '/api/cAdminMgt/getAdmin/'+$scope.currentPage+'/'+$scope.pageItem.value
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
	
	updateAll();
	
	function updateAll(){
		$http({
			method : 'GET',
			url : '/api/cAdminMgt/getAdmin/'+$scope.currentPage+'/'+$scope.pageItem.value
		}).success(function(data){
			console.log(data);
			$scope.itms = data.result;
			
			$scope.totalItems = data.totalArticle;
			$scope.currentPage = data.currentPage;
			$scope.perPage = data.perArticle;
			$scope.startNo = data.startArticleNo;
			
		}).error(function(){
			
		});
	}
	
	$scope.viewAdmin = function(seq){
		
		for(var i=0;i<$scope.itms.length;i++){
			if($scope.itms[i].seq == seq){
				$scope.selectedAdmin = $scope.itms[i];
			}
		}
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/adminMgt/adminModal.html',
			controller : 'adminModalCtrl',
			size : 'lg',
			resolve : {
				itm : function() {
					return {status : 1, data: $scope.selectedAdmin};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			updateAll();
		}, function() {
	
		});
		
	}
	
	$scope.rgtAdmin = function(){
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cConfig/modal/adminMgt/adminModal.html',
			controller : 'adminModalCtrl',
			size : 'lg',
			resolve : {
				itm : function() {
					return {status : 3, data: $scope.selectedAdmin};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			updateAll();
		}, function() {
	
		});
	}
	
}])
.controller('adminModalCtrl', function($scope, $http, $modalInstance, itm) {
	
	$scope.status = itm.status;
	var historyItm = {};
	historyItm.accessMenu = '환경설정/계정관리';
	historyItm.accessTable = "af_admin";
	
	if($scope.status == 3){
		$scope.idChk = 0;
		$scope.itm = {};
		$scope.itm.pw_1 = "";
		$scope.itm.pw_2 = "";
	}else{
		$scope.itm = itm.data;
		
		$scope.itm.pwChg = 0;
		$scope.editStatus = 0;
		
		$scope.itm.currentPw = "";
		$scope.itm.changePw = "";
		$scope.itm.configmPw = "";
	}
	
	$scope.cancle = function(){
		
		if($scope.status == 2){
			if($scope.newItms.name != $scope.itm.name){
				$scope.itm.name = $scope.newItms.name;
			}
			if($scope.newItms.email != $scope.itm.email){
				$scope.itm.email = $scope.newItms.email;
			}
			if($scope.newItms.tel != $scope.itm.tel){
				$scope.itm.tel != $scope.newItms.tel;
			}
			$scope.editStatus = 0;
			$scope.status = 1;
		}else{
			$modalInstance.dismiss('cancle');
		}
	}
	
	$scope.editAdmin = function(){
		if($scope.status == 1){
			
			$scope.itm.pwChg = 0;
			$scope.editStatus = 0;
			
			$scope.itm.pw = "";
			$scope.itm.currentPw = "";
			$scope.itm.changePw = "";
			$scope.itm.configmPw = "";
			
			$scope.newItms = {};
			$scope.newItms.name = $scope.itm.name;
			$scope.newItms.email = $scope.itm.email;
			$scope.newItms.tel = $scope.itm.tel;
			
			$scope.status = 2;
		}else if($scope.status == 2){
			
			if($scope.itm.name == null || $scope.itm.name == "" || $scope.itm.name == undefined){
				alert("이름을 입력해 주세요.");
				return;
			}
			if($scope.itm.email == null || $scope.itm.email == "" || $scope.itm.email == undefined){
				alert("EMAIL을 입력해 주세요.");
				return;
			}
			if($scope.itm.tel == null || $scope.itm.tel == "" || $scope.itm.tel == undefined){
				alert("TEL을 입력해 주세요.");
				return;
			}
			
			if($scope.itm.pwChg == 0){
				$scope.editStatus = 1;
				
				if($scope.itm.currentPw == null || $scope.itm.currentPw == "" || $scope.itm.currentPw == undefined){
					alert("현재 비밀번호를 입력해주세요.");
					return;
				}
				
				$scope.itm.pw = $scope.itm.currentPw;
				var request = $http.post("/api/cAdminMgt/chkPw", $scope.itm);
				request.then(function(regitm) {
					
					if(regitm.data.data == 0){
						alert("현재 비밀번호가 일치하지 않습니다.");
						return;
					}else{
						var c = confirm("수정하시겠습니까?");
						if(c == true){
							updateAdmin($scope.itm);
						}else{
							return;
						}
					}
				}, function(result) {
					if (result.data.code != 10)
						alert(result.data.message);
					else
						alert(result.data.message);
				});
				
				
			} else if($scope.itm.pwChg == 1){
				
				if($scope.itm.currentPw == null || $scope.itm.currentPw == "" || $scope.itm.currentPw == undefined){
					alert("현재 비밀번호를 입력해주세요.");
					return;
				}
				if($scope.itm.changePw == null || $scope.itm.changePw == "" || $scope.itm.changePw == undefined){
					alert("변경 비밀번호를 입력해주세요.");
					return;
				}
				if($scope.itm.confirmPw == null || $scope.itm.confirmPw == "" || $scope.itm.confirmPw == undefined){
					alert("변경 비밀번호 확인을 입력해주세요.");
					return;
				}
				if($scope.itm.changePw != $scope.itm.confirmPw){
					alert("변경 비밀번호가 일치하지 않습니다.");
					return;
				}
				
				$scope.itm.pw = $scope.itm.currentPw;
				var request = $http.post("/api/cAdminMgt/chkPw", $scope.itm);
				request.then(function(regitm) {
					
					if(regitm.data.data == 0){
						alert("현재 비밀번호가 일치하지 않습니다.");
						return;
					}else{
						$scope.itm.pw = $scope.itm.changePw;
						
						var c = confirm("수정하시겠습니까?");
						if(c == true){
							updateAdmin($scope.itm);
						}else{
							return;
						}
					}
				}, function(result) {
					if (result.data.code != 10)
						alert(result.data.message);
					else
						alert(result.data.message);
				});
				
			}
		}
	}
	
	function updateAdmin(itm){
		var request = $http.post("/api/cAdminMgt/updateAdmin", itm);
		request.then(function(regitm) {
			
			historyItm.action = "U";
			historyItm.accessId = $scope.itm.id;
			
			$http.post('/api/insertPageHistory', historyItm)
		    .then( function(resp) {
		    	alert("수정되었습니다.");
				$scope.itm.pwChg = 0;
				$scope.editStatus = 0;
				$scope.status = 1;
		    }, function(resp){
		    	
		    }); 
			
			
		}, function(result) {
			if (result.data.code != 10)
				alert(result.data.message);
			else
				alert(result.data.message);
		});
	}
	
	$scope.chkId = function(){
		
		if($scope.itm.id == null || $scope.itm.id == "" || $scope.itm.id == undefined){
			alert("ID를 입력하세요.");
			return;
		}
		
		$http({
			method : 'GET',
			url : '/api/cAdminMgt/chkId/'+$scope.itm.id
		}).success(function(data){
			
			if(data.data == 1){
				alert('사용 가능한 ID 입니다.');
				$scope.idChk = 1;
			}else if(data.data == 0){
				alert('이미 사용중인 ID입니다. 다른 ID를 입력하세요.');
				return;
			}
			
			
		}).error(function(){
			
		});
	}
	
	$scope.regAdmin = function(){
		
		if($scope.itm.id == null || $scope.itm.id == "" || $scope.itm.id == undefined){
			alert("ID를 입력하세요.");
			return;
		}
		if($scope.idChk == 0){
			alert("ID 중복확인을 해 주세요.");
			return;
		}
		
		if($scope.itm.pw_1 == null || $scope.itm.pw_1 == "" || $scope.itm.pw_1 == undefined){
			alert("비밀번호를 입력하세요.");
			return;
		}
		if($scope.itm.pw_2 == null || $scope.itm.pw_2 == "" || $scope.itm.pw_2 == undefined){
			alert("비밀번호 확인을 입력하세요.");
			return;
		}
		if($scope.itm.pw_1 != $scope.itm.pw_2){
			alert("비밀번호가 일치하지 않습니다.");
			return;
		}
		if($scope.itm.name == null || $scope.itm.name == "" || $scope.itm.name == undefined){
			alert("이름을 입력해 주세요.");
			return;
		}
		if($scope.itm.email == null || $scope.itm.email == "" || $scope.itm.email == undefined){
			alert("EMAIL을 입력해 주세요.");
			return;
		}
		if($scope.itm.tel == null || $scope.itm.tel == "" || $scope.itm.tel == undefined){
			alert("TEL을 입력해 주세요.");
			return;
		}
		
		$scope.itm.pw = $scope.itm.pw_1;
		
		var c = confirm("등록하시겠습니까?");
		if(c == true){
			var request = $http.post("/api/cAdminMgt/insertAdmin", $scope.itm);
			request.then(function(regitm) {
				
				historyItm.action = "I";
				historyItm.accessId = $scope.itm.id;
				
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
	}
	
	$scope.delAdmin = function(){
		var c = confirm("삭제하시겠습니까?");
		if(c == true){
			var request = $http.post("/api/cAdminMgt/deleteAdmin", $scope.itm);
			request.then(function(regitm) {
				
				historyItm.action = "D";
				historyItm.accessId = $scope.itm.id;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
			    	alert("삭제되었습니다.");
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
	}
	
	
})
;