'use strict';
angular.module('AfpcApp')
.directive('sidebar', function() {
	var thisController = ['$scope', '$timeout', '$http', '$location', '$window',
	                      function($scope, $timeout, $http, $location, $window){
		
		  $scope.groups = [
			    {
			      title: "점검 대상 관리",
			      icon: "fa fa-desktop",
			      content: [{name: "Agent 관리", icon: "fa fa-desktop", path: "/page/cAgent#/base/cAgent"}],
			      open: false
			    },
			    {
			      title: "점검 항목 관리",
			      icon: "fa fa-list",
			      content: [{name: "항목 관리", icon: "fa fa-list", path: "/page/cList#/base/cList"}],
			      open: false
			    },
			    {
			      title: "점검 수행 관리",
			      icon: "fa fa-calendar",
			      content: [{name: "점검 수행일 관리", icon: "fa fa-clock-o", path: "/page/cCheck#/base/cSchedule"}, {name: "점검 결과", icon: "fa fa-check", path: "/page/cCheck#/base/cResult"}],
			      open: false
			    },
			    {
			      title: "점검 결과 관리",
			      icon: "fa fa-line-chart",
			      content: [{name: "통계", icon: "fa fa-bar-chart", path: "/page/cResult#/base/cChart"},{name: "Report", icon: "fa fa-book", path: "/page/cResult#/base/cReport"}],
			      open: false
			    },
			    {
			      title: "환경설정",
			      icon: "fa fa-cogs",
			      content: [{name: "License", icon: "fa fa-check", path: "/page/cConfig#base/cLicenseMgt"},{name: "Patch", icon: "fa fa-hourglass", path: "/page/cConfig#base/cPatchMgt"},{name: "부서 관리", icon: "fa fa-sitemap", path: "/page/cConfig#base/cDepartMgt"},{name: "계정 관리", icon: "fa fa-user", path: "/page/cConfig#/base/cAdminMgt"},{name: "이력 조회", icon: "fa fa-search", path: "/page/cConfig#base/cHistoryMgt"}],
			      open: false
				    }
			  ];
		}
	
	];
	return {
		templateUrl : '/apps/commons/views/sidebar.html',
		restrict : 'E',
		replace : true,
		controller: thisController
	}
});