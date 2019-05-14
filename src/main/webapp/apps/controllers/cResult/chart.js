'use strict';
angular.module('AfpcApp')
.constant('uiCalendarConfig',{
	calendars : {}
})
	.controller('cResult.chartCtrl', [ '$scope', '$http', '$modal', '$location','$locale',
		function($scope, $http, $modal, $location, itm,$locale,data) {

			$scope.series4 = [ '양호', '취약' ];

			$scope.series3 = [ '양호', '취약' ];

			$scope.labels = [ "수행일 정기 점검", "사용자 지정 점검" ];
			
			
			updateAll();

	function updateAll(){
		
			$http({
				method : 'GET',
				url : '/api/cChart/AutoCnt'
			}).success(function(data) {
				console.log(data);
				$scope.data = data;
			}).error(function() {
				alert("error");
			});

			// --------------------------------------------------------

			$http({
				method : 'GET',
				url : '/api/cChart/getCheckDate'
			}).success(function(labels2) {
				$scope.labels2 = labels2;
			}).error(function() {
				alert("error");
			});


			$http({
				method : 'GET',
				url : '/api/cChart/cntAgentByDate'
			}).success(function(data2) {
				$scope.data2 = data2;
			}).error(function() {
				alert("error");
			});

			// ------------------------------------

			$http({
				method : 'GET',
				url : '/api/cChart/getCheckDate'
			}).success(function(labels3) {
				$scope.labels3 = labels3;
			}).error(function() {
				alert("error");
			});



			$http({
				method : 'GET',
				url : '/api/cChart/ResultCnt'
			}).success(function(data3) {
				$scope.data3 = data3;
			}).error(function() {
				alert("error");
			});

			// ----------------------------------------


			$http({
				method : 'GET',
				url : '/api/cChart/getTeam'
			}).success(function(labels4) {
				$scope.labels4 = labels4;
			}).error(function() {
				alert("error");
			});



			$http({
				method : 'GET',
				url : '/api/cChart/getTeamResult'
			}).success(function(data4) {
				$scope.data4 = data4;
				console.log(data4);
			}).error(function() {
				alert("data4 error");
			});
			
			
			
			$http({
				method : 'GET',
				url : '/api/cChart/getResult1'
			}).success(function(data5) {
				
				console.log(data5);
				
				
				$scope.labels5 = [];
				$scope.data5 = [];
				$scope.series5 = [ '양호', '취약' ];
				
				$scope.safeAry = [];
				$scope.dangerAry = [];
				
				for(var i=0;i<data5.length;i++){
					$scope.labels5.push(data5[i].first);
					$scope.safeAry.push(data5[i].second);
					$scope.dangerAry.push(data5[i].third);
				}
				
				$scope.data5.push($scope.safeAry);
				$scope.data5.push($scope.dangerAry);
				
				
			}).error(function() {
				alert("data5 error");
			});
			
			
			

	}

			//		    $scope.data4 = [
			//		      [65, 59, 80, 81, 56, 55, 40],
			//		      [28, 48, 40, 19, 86, 27, 90]
			//		    ];

			// ---------------------------------------------------
			
	 $scope.eventSource = {
		    	url: ""
		    };	
	
	$scope.eventsF = function (start,end,timezone,callback){
		var s = new Date(start).getTime() / 1000;
		var e = new Date(end).getTime() / 1000;
		var m = new Date(start).getMonth();
		updateAll();
		 var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
	      callback(events);
	};
	
	
		$scope.regDate = function(){
			var modalInstance = $modal
			.open({
				animation : $scope.animationsEnabled,
				templateUrl : '/apps/views/cResult/modal/chartModal.html',
				controller : 'chartModalCtrl',
				resolve : {
					itm : function(){
						return {};
					}
				}
				
			});
			modalInstance.result.then(function(itm){
				
			}, function (){
				
			});
			
			
		}
			
			
		



		} ])
.controller('chartModalCtrl',function($scope,$http,$modalInstance,$filter,itm,$window){
	
	$scope.itm = {};
	
	$scope.options1 = {
			
			format : "YYYY-MM-DD",
			singleDatePicker : true,
			showDropdowns : true
			
	}
	
	$scope.cancel = function (){
		$modalInstance.close();
	}
	
	$scope.searchDate = function(){
		var sDate = $filter('date')($scope.itm.startDate._d,'yyyy-MM-dd');
		var eDate = $filter('date')($scope.itm.endDate._d,'yyyy-MM-dd');
		
		
		$http({
			method : 'GET',
			url : '/api/cChart/getResultCnt/'+sDate+'/'+eDate			
		}).success(function(data){
			console.log(data);
			$scope.data = data;
		}).error(function(){
			alert("error");
		});
		
		
		$http({
			method : 'GET',
			url : '/api/cChart/cntAgentSearchByDate/'+sDate+'/'+eDate
		}).success(function(data){
			$scope.data = data;
		}).error(function(){
			alert("error");
		});
		
		
		
	}
	
	
	
})		
		
		
		
		
		