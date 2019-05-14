<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app='AfpcApp'>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<link rel="shortcut icon" href="img/favicon_af.png">
	
	<script src="/lib/angular/angular.min.js"></script>
	<script src="/lib/angular-cookies/js/angular-cookies.min.js"></script>
	<script src="/lib/angular-bootstrap/js/ui-bootstrap-tpls.min.js"></script>
	<link href="/lib/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/css/style.css" rel="stylesheet"> 
	<link rel="stylesheet" href="/lib/fontawesome/css/font-awesome.min.css" type="text/css">
	
	<script type="text/javascript" src="/lib/crypto/js/jsbn.js"></script>
	<script type="text/javascript" src="/lib/crypto/js/rsa.js"></script>
	<script type="text/javascript" src="/lib/crypto/js/prng4.js"></script>
	<script type="text/javascript" src="/lib/crypto/js/rng.js"></script>
	
	<script >
		angular.module('AfpcApp', ['ngCookies','ui.bootstrap'])
		.controller("afpc.login.ctrl",[ "$cookies", "$scope", "$http",  function($cookies, $scope, $http) {
			$scope.alerts=[];
			
			var idstr = $cookies.get('_soc_id_remember');
			if( !angular.isUndefined(idstr)) {
				$scope.p_username = idstr;
				$scope.p_idremember = 1;
			} else {
				$scope.p_username = "";
				$scope.p_idremember = 0;
			}
			
			$scope.p_password = "";
			
			$scope.keyCheck = function(e) {
				var ieKey = e.keyCode;
				if(ieKey == 13 ) {
					if(e.srcElement.name == "p_username") 
						document.getElementById('p_password').focus();
					else if( e.srcElement.name =='p_password')
						$scope.loginstart();
				}
			};
			
			$scope.closeAlert = function(index) {
			    $scope.alerts.splice(index, 1);
			};
			
			$scope.loginstart = function() {
				
				if($scope.p_username == null || $scope.p_username.length == 0 || $scope.p_username == 'undefined'){
					alert('ID를 입력하세요.');
					return;
				}
				if($scope.p_password == null || $scope.p_password.length == 0 || $scope.p_password == 'undefined'){
					alert('비밀번호를 입력하세요.');
					return;
				}
				
			 	if( $scope.p_idremember == 1) {
					var exdate = new Date();
					exdate.setDate( exdate.getDate() +7);
					var copt = { 'expires' : exdate }
					$cookies.put('_soc_id_remember', $scope.p_username, copt);
				} else {
					$cookies.remove('_soc_id_remember');
				} 
				
				var k1 = '${KeyModules}';
				var k2 = '${KeyExp}';
				var rsa = new RSAKey();
				rsa.setPublic(k1, k2);
				var idpwitm = {};
				idpwitm.id = rsa.encrypt($scope.p_username);
				idpwitm.pw = rsa.encrypt($scope.p_password);
				
				$http.post('/api/login_check', idpwitm)
				    .then( function(resp) {
				    	if( resp.data.code == 10) {
				    		var goUrl = resp.data.exinfo;
				    		if( goUrl != '') 
				    			location.href = goUrl;
				    		else 
				    			location.href = '/page/cMain';
				    	} else {
				    		alert("아이디 또는 비밀번호를 다시 확인하세요.");
				    	}
				    }, function(resp){
				    	$scope.alerts.push({type:'danger', msg: '요청 Error : ' + resp.statusText + '(' + resp.status + ')' });
				    }); 
			};
			
		} ]);

	</script>	
</head>
<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden" ng-controller="afpc.login.ctrl" >
	<div class="app flex-row align-items-center">
	    <div class="container">
	        <div class="row justify-content-center">
	            <div class="col-md-8">
	                <div class="card-group mb-0">
	                    <div class="card p-4">
	                        <div class="card-block">
		                        <form role="form" id='socloginform' name='socloginform' onsubmit='return false;' >
		                            <h1>Login</h1>
		                            <p class="text-muted">Sign In to your account</p>
			                            <div class="input-group mb-3">
			                                <span class="input-group-addon">
			                                	<i class="fa fa-user"></i>
			                                </span>
	                                		<input type="text" class="form-control" placeholder="ID" name="p_username" id="p_username" ng-model="p_username" ng-keypress="keyCheck($event)">
		                            	</div>
			                            <div class="input-group mb-4">
			                                <span class="input-group-addon">
			                                	<i class="fa fa-lock"></i>
			                                </span>
			                                <input type="password" class="form-control" placeholder="Password"  name="p_password" id="p_password" ng-model="p_password" ng-keypress="keyCheck($event)">
			                            </div>
			                            <div class="row">
			                                <div class="col-6">
				                                <div class="checkbox">
													<label> <input name="p_idremember" type="checkbox" ng-model="p_idremember" ng-true-value="1" ng-false-value="0"> Remember ID! 
													</label>
												</div>
			                                </div>
			                                <div class="col-6 text-right">
			                                   <button type="button" class="btn btn-primary px-4" ng-click="loginstart()">Login&nbsp;&nbsp;<span class="fa fa-plane"></span></button>
			                                </div>
			                            </div>
		                            </form>
	                        	</div>
	                    </div>
	                    <div class="card card-inverse card-primary py-5 d-md-down-none" style="width:44%">
	                        <div class="card-block text-center">
	                            <div>
	                                <h2>AuditFinder PC</h2>
	                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
	                                <button type="button" class="btn btn-primary active mt-3">Register Now!</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div> 
	    </div>
	</div>
</body>
</html>