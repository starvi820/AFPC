'use strict';
angular.module('AfpcApp')
.constant('uiCalendarConfig', {
        calendars : {}
    })
.controller('cCheck.scheduleCtrl',[ '$scope', '$http', '$modal', '$location', '$locale', 
						function($scope, $http, $modal, $location, $locale) {
	
	var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    
    $scope.data = [];
    $scope.events = [];
    
    function updateAll(){
    	$http({
    		method : 'GET',
    		url : '/api/cCheck/getSchedule'
    	}).success(function(data) {
    		
    		var today = new Date();
    		
    		$scope.data = data;
    		for(var i=0;i<$scope.data.length;i++){
    			
    			$scope.data[i].chkBtn = false;
    			var cDate = new Date($scope.data[i].year, $scope.data[i].month-1, $scope.data[i].day);
    			
    			if(today < cDate){
    				$scope.data[i].chkBtn = true;
    			}
    			$scope.events.push({seq: $scope.data[i].seq, title: $scope.data[i].title, start: new Date($scope.data[i].year, $scope.data[i].month-1, $scope.data[i].day), chkBtn: $scope.data[i].chkBtn });
    		}
    	}).error(function() {
    		
    	})
    }
    
    $scope.eventSource = {
    	url: ""
    };

    $scope.eventsF = function (start, end, timezone, callback) {
      var s = new Date(start).getTime() / 1000;
      var e = new Date(end).getTime() / 1000;
      var m = new Date(start).getMonth();
      updateAll();
      var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
      callback(events);
    };
    
    /* alert on eventClick */
    $scope.alertOnEventClick = function( date, jsEvent, view){
        console.log(date.title + ' was clicked ');
        
        var itm = {};
        for(var i=0;i<$scope.data.length;i++){
        	if($scope.data[i].title == date.title){
        		itm = $scope.data[i];
        	}
        }
        var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cCheck/modal/scheduleViewModal.html',
			controller : 'scheduleViewModalCtrl',
			resolve : {
				itm : function() {
					return {status: 1, itm: itm};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			 
		}, function() {
	
		});
    };
    
    /* alert on Drop */
     $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
       $scope.alertMessage = ('Event Droped to make dayDelta ' + delta);
//       alert("날짜를 이동하신 경우 반드시 하단의 저장 버튼을 눌러주세요.");
       
       location.reload();
       
       console.log(delta);
       
        
       
    };
   
    /* config object */
    $scope.uiConfig = {
      calendar:{
        height: 650,
        editable: true,
        header:{
          left: 'month',
          center: 'title',
          right: 'today prev,next'
        },
        eventClick: $scope.alertOnEventClick,
        eventDrop: $scope.alertOnDrop
      }
    };
    
    /* event sources array*/
    $scope.eventSources = [$scope.events, $scope.eventSource, $scope.eventsF];
		
    $scope.regSchedule = function(){
    	var modalInstance = $modal
		.open({
			animation : $scope.animationsEnabled,
			templateUrl : '/apps/views/cCheck/modal/schedule.html',
			controller : 'scheduleModalCtrl',
			resolve : {
				itm : function() {
					return {};
				}
			}
		});
		modalInstance.result.then(function(itm) {
			
		}, function() {
	
		});
    }
    
}])
.controller('scheduleViewModalCtrl', function($scope, $http, $modalInstance, $filter, itm, $window) {
	
	var historyItm = {};
	historyItm.accessTable = "af_schedule";
	historyItm.accessMenu = "점검수행관리/수행점검일관리";
	
	$scope.options = {
		format: "YYYY-MM-DD",
		singleDatePicker: true,
        showDropdowns: true
        
        
    }
	
	$scope.status = itm.status;
	$scope.itm = itm.itm;
	
	$scope.delete = function(){
		
		var c = confirm("삭제하시겠습니까?");
		if(c == true){
			var request = $http.post("/api/cCheck/deleteSchedule/"+$scope.itm.seq);
			request.then(function(regitm) {
				
				historyItm.action = "D";
				historyItm.accessId = $scope.itm.title;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
			    	alert("삭제되었습니다.");
					//$modalInstance.close(regitm.config.data);
			    	$window.location.reload();
			    })
				
			},function(result) {
				
			});
		}else{
			return;
		}
		
	}
	
	$scope.edit = function(){
		if($scope.status == 1){
			$scope.itm.chkDate = $scope.itm.checkDate;
			$scope.itm.oldChkDate = $scope.itm.checkDate;
			$scope.status = 2;
		}else if($scope.status == 2){
			
			if($scope.itm.title == null || $scope.itm.title == "" || $scope.itm.title == undefined){
				alert("점검명을 입력해 주세요.");
				return;
			}
			
			if($scope.itm.chkDate == undefined || $scope.itm.chkDate == null){
				alert("점검 수행일을 선택해주세요.");
				return;
			}
			
			if( new Date() > $scope.itm.chkDate._d){
				alert("오늘 이전의 날짜는 선택하실 수 없습니다.");
				return;
			}
			
			if($scope.itm.oldChkDate == $scope.itm.chkDate){
				$scope.itm.checkDate = $scope.itm.chkDate;
			}else{
				$scope.itm.checkDate = $filter('date')($scope.itm.chkDate._d, 'yyyy-MM-dd');
			}
			
			var dateItm = $scope.itm.checkDate.split("-");
			$scope.itm.year = dateItm[0];
			$scope.itm.month = dateItm[1];
			$scope.itm.day = dateItm[2];
			
			if($scope.itm.month.substring(0,1) == '0')
				$scope.itm.month = $scope.itm.month.substring(1,2);
			
			if($scope.itm.day.substring(0,1) == '0')
				$scope.itm.day = $scope.itm.day.substring(1,2);
			
			var c = confirm("수정하시겠습니까?");
			if(c == true){
				var request = $http.post("/api/cCheck/updateSchedule" ,$scope.itm);
				request.then(function(regitm) {
					
					historyItm.action = "U";
					historyItm.accessId = $scope.itm.title;
					
					$http.post('/api/insertPageHistory', historyItm)
				    .then( function(resp) {
				    	alert("수정되었습니다.");
						//$modalInstance.close(regitm.config.data);
				    	$window.location.reload();
				    })
					
				},function(result) {
					
				});
			}else{
				return;
			}
		}
	}
	
	$scope.cancle = function(){
		$modalInstance.close();
	}
})
.controller('scheduleModalCtrl', function($scope, $http, $modalInstance, $filter, itm, $window) {
	
	var historyItm = {};
	historyItm.accessTable = "af_schedule";
	historyItm.accessMenu = "점검수행관리/수행점검일관리";
	
	$scope.itm = {};
	$scope.itm.chkDate = null;
	
	$scope.options = {
		format: "YYYY-MM-DD",
		singleDatePicker: true,
        showDropdowns: true
    }

	$scope.cancle = function(){
		$modalInstance.close();
	}
	
	$scope.regSchedule = function(){
		
		if($scope.itm.title == null || $scope.itm.title == "" || $scope.itm.title == undefined){
			alert("점검명을 입력해 주세요.");
			return;
		}
		
		if($scope.itm.chkDate == undefined || $scope.itm.chkDate == null){
			alert("점검 수행일을 선택해주세요.");
			return;
		}
		
		if( new Date() > $scope.itm.chkDate._d){
			alert("오늘 이전의 날짜는 선택하실 수 없습니다.");
			return;
		}
		
		$scope.itm.checkDate = $filter('date')($scope.itm.chkDate._d, 'yyyy-MM-dd');
		
		var dateItm = $scope.itm.checkDate.split("-");
		$scope.itm.year = dateItm[0];
		$scope.itm.month = dateItm[1];
		$scope.itm.day = dateItm[2];
		
		if($scope.itm.month.substring(0,1) == '0')
			$scope.itm.month = $scope.itm.month.substring(1,2);
		
		if($scope.itm.day.substring(0,1) == '0')
			$scope.itm.day = $scope.itm.day.substring(1,2);
		
		var c = confirm("등록하시겠습니까?");
		if(c == true){
			
			var request = $http.post("/api/cCheck/regitSchedule" ,$scope.itm);
			request.then(function(regitm) {
				
				historyItm.action = "I";
				historyItm.accessId = $scope.itm.title;
				
				$http.post('/api/insertPageHistory', historyItm)
			    .then( function(resp) {
			    	alert("등록되었습니다.");
//					$modalInstance.close(regitm.config.data);
			    	$window.location.reload();
			    })
			},function(result) {
				
			});
		}else{
			return;
		}
		
		
	}
	
})
;