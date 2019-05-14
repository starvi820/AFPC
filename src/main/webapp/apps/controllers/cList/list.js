'use strict';
angular.module('AfpcApp')
.controller('cList.listCtrl',[ '$scope', '$http', '$modal', '$location', 
						function($scope, $http, $modal, $location) {
	
	$scope.viewStatus = 0;
	updateAll();
	
	function updateAll(){
		$http({
			method : 'GET',
			url : '/api/cList/getList'
		}).success(function(data){
			$scope.itms = data;
			
		}).error(function(){
			
		});
	}
	
	
	$scope.viewList = function(seq){
		for(var i=0;i<$scope.itms.length;i++){
			if($scope.itms[i].seq == seq){
				var lItm = $scope.itms[i];
				
			}
		}
		
		
	
		
		
		
		var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cList/modal/listModal.html',
			controller : 'listModalCtrl',
			size : 'lg',
			resolve : {
				itm : function() {
					return {status : 1, itm : lItm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			updateAll();
		}, function() {
	
		});
	}
	
	
}])
.controller('listModalCtrl', function($scope, $http, $modalInstance, itm, Upload, $location) {
	
	$scope.result = {};
	
	$scope.status = itm.status;
	$scope.itm = itm.itm;
	$scope.result.useYn = $scope.itm.listUseYn;
	
	$scope.cancle = function(){
		if($scope.status == 1){
			$modalInstance.close();
		}else if($scope.status == 2){
			if($scope.newItms.listName != $scope.itm.listName){
				$scope.itm.listName = $scope.newItms.listName;
			}
			if($scope.newItms.listContent != $scope.itm.listContent){
				$scope.itm.listContent = $scope.newItms.listContent;
			}
			if($scope.newItms.listSolution != $scope.itm.listSolution){
				$scope.itm.listSolution = $scope.newItms.listSolution;
			}
			$scope.status = 1;
		}
	}
	
	$scope.editList = function(){
		if($scope.status == 1){
			
			$scope.newItms = {};
			$scope.newItms.listName = $scope.itm.listName;
			$scope.newItms.listContent = $scope.itm.listContent;
			$scope.newItms.listSolution = $scope.itm.listSolution;
			
			$scope.status = 2;
		}else{
			
			if($scope.itm.listName == null || $scope.itm.listName == "" || $scope.itm.listName == undefined){
				alert("이름을 입력하세요.");
				return;
			}
			if($scope.itm.listContent == null || $scope.itm.listContent == "" || $scope.itm.listContent == undefined){
				alert("내용을 입력하세요.");
				return;
			}
			if($scope.itm.listSolution == null || $scope.itm.listSolution == "" || $scope.itm.listSolution ==  undefined){
				alert("Solution을 입력하세요.");
				return;
			}
			
			$scope.itm.listUseYn = $scope.result.useYn;
			
			var c = confirm("수정하시겠습니까?");
			if(c == true){
				
				var request = $http.post("/api/cList/updateList/", $scope.itm);
				request.then(function(regitm) {
					
					var historyItm = {};
					historyItm.accessMenu = "점검항목관리/점검항목관리";
					historyItm.accessTable = "af_check_list"
					
					historyItm.action = "U";
					historyItm.accessId = $scope.itm.listName;
					
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
	
})
;