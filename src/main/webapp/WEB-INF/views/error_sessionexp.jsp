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
	<link href="/resources/css/style.css" rel="stylesheet"> 
	<link rel="stylesheet" href="/lib/fontawesome/css/font-awesome.min.css" type="text/css">
	
</head>
<div class="app flex-row align-items-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="clearfix" style="margin-top: -200px;">
                    <h1 class="float-left display-3 mr-4"><i class="fa fa-ban"></i></h1>
                    <h4 class="pt-3">Problems!!! Your Session has expired.</h4>
                    <p class="text-muted">세션이 만료되었습니다. </p>
                </div>
            </div>
        </div>
        <div class="row justify-content-center" style="margin-top: -100px;">
        	<a href="/page/cLogin"><button type="button" class="btn btn-secondary"> 로그인페이지로 이동&nbsp;&nbsp;<i class="fa fa-plane"></i></button></a>
        </div>
    </div>
</div>
</body>
</html>