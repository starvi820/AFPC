<!doctype html>
<html class="no-js">
	<div ng-app="AfpcApp">
		<head>
		<title>AuditFinder</title>
		
		<link href="resources/css/style.css" rel="stylesheet"> 
		<link rel="stylesheet" href="/lib/fontawesome/css/font-awesome.min.css" type="text/css">
		
		<script src="/lib/jquery/js/jquery.min.js"></script>
		<script src="/lib/angular/js/angular.min.js"></script>
		<script src="/lib/angular-ui-router/js/angular-ui-router.min.js"></script>
		<script src="/lib/angular-bootstrap/js/ui-bootstrap-tpls.min.js"></script>
		<script src="/lib/angular-resource/js/angular-resource.min.js"></script>
		<script src="/lib/angular-sanitize/js/angular-sanitize.min.js"></script>
		<script src="/lib/angular-cookies/js/angular-cookies.min.js"></script>
		<script src="/lib/angular-touch/js/angular-touch.min.js"></script>
		<script src="/lib/angular-animate/js/angular-animate.min.js"></script>
		<script src="/lib/ng-file-upload/js/ng-file-upload-shim.min.js"></script>
		<script src="/lib/ng-file-upload/js/ng-file-upload.min.js"></script>
		
		<script src="https://code.highcharts.com/highcharts.js"></script>
		<script src="https://code.highcharts.com/highcharts-more.js"></script>

		<script src="https://code.highcharts.com/modules/solid-gauge.js"></script>
		
	
		<script src="/lib/d3/js/d3.min.js" charset="utf-8"></script>
	    <script src="/lib/nvd3/js/nv.d3.min.js"></script>
	    <script src="/lib/angular-nvd3/js/angular-nvd3.js"></script>
	    <link rel="stylesheet" href="/lib/nvd3/css/nv.d3.min.css"/> 
	    
		<script src="/apps/cMain.js"></script>
		<script src="/apps/commons/script/str.js"></script>
		
		<script src="/apps/commons/service/config.js"></script>
		<script src="/apps/commons/controllers/header.js"></script>
		<script src="/apps/commons/controllers/headerconfig.js"></script>
		<script src="/apps/commons/controllers/sidebar.js"></script>
		<script src="/apps/commons/filters/commonfilter.js"></script>
		
		<script src="/apps/controllers/cMain/base.js"></script>
		<script src="/apps/controllers/cMain/main.js"></script>
		</head>
		
		<body>
			<div ui-view></div>
		</body>
		
	</div>
</html>
