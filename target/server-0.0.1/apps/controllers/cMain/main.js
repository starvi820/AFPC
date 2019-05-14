'use strict';
angular.module('AfpcApp')
.controller('cMain.MainCtrl',[ '$scope', '$http', '$interval', 
						function($scope, $http, $interval) {
	
	var theInterval = $interval(function(){
		pageReload();
	}.bind(this), 60000);
	
	$scope.$on('$destroy', function () {
        $interval.cancel(theInterval)
    });
	
	pageReload();
	
	function pageReload() {
		$http({
			method : 'GET',
			url : '/api/cMain/getSysInfo'
		}).success(function(data){
			
			$scope.sysInfo = data;
			
			$scope.sysInfo.tm = data.tm;
			$scope.sysInfo.cpu = data.cpu/100;
			$scope.sysInfo.pmem = data.pmem/100;
			$scope.sysInfo.disk = data.disk/100;
			
		    var gaugeOptions = {
		    		
		        chart: {
		            type: 'solidgauge'
		        },
		
		        title: null,
		
		        pane: {
		            center: ['50%', '85%'],
		            size: '140%',
		            startAngle: -90,
		            endAngle: 90,
		            background: {
		                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
		                innerRadius: '60%',
		                outerRadius: '100%',
		                shape: 'arc'
		            }
		        },
		
		        tooltip: {
		            enabled: false
		        },
		
		        // the value axis
		        yAxis: {
		            stops: [
		                [0.1, '#55BF3B'], // green
		                [0.5, '#DDDF0D'], // yellow
		                [0.9, '#DF5353'] // red
		            ],
		            lineWidth: 0,
		            minorTickInterval: null,
		            tickAmount: 2,
		            title: {
		                y: -70
		            },
		            labels: {
		                y: 16
		            }
		        },
		
		        plotOptions: {
		            solidgauge: {
		                dataLabels: {
		                    y: 5,
		                    borderWidth: 0,
		                    useHTML: true
		                }
		            }
		        }
		    };
		
		    var chartCPU = Highcharts.chart('container-cpu', Highcharts.merge(gaugeOptions, {
		    	
		        yAxis: {
		            min: 0,
		            max: 100,
		            title: {
		                text: 'CPU'
		            }
		        },
		
		        credits: {
		            enabled: false
		        },
		
		        series: [{
		            name: 'CPU',
		            data: [$scope.sysInfo.cpu],
		            dataLabels: {
		                format: '<div style="text-align:center"><span style="font-size:20px;color:#3A3C3D;">{y}</span><br/>' +
		                       '<span style="font-size:18px;color:#4F5558">%</span></div>'
		            },
		            tooltip: {
		                valueSuffix: ' %'
		            }
		        }]
		
		    }));
		    
		    var chartMEM = Highcharts.chart('container-memory', Highcharts.merge(gaugeOptions, {
		        yAxis: {
		            min: 0,
		            max: 100,
		            title: {
		                text: 'MEMORY'
		            }
		        },
		
		        credits: {
		            enabled: false
		        },
		
		        series: [{
		            name: 'MEMORY',
		            data: [$scope.sysInfo.pmem],
		            dataLabels: {
		                format: '<div style="text-align:center"><span style="font-size:20px;color:#3A3C3D;">{y}</span><br/>' +
		                       '<span style="font-size:18px;color:#4F5558">%</span></div>'
		            },
		            tooltip: {
		            	valueSuffix: ' %'
		            }
		        }]
		
		    }));
		    
		    var chartDISK = Highcharts.chart('container-disk', Highcharts.merge(gaugeOptions, {
		        yAxis: {
		            min: 0,
		            max: 100,
		            title: {
		                text: 'DISK'
		            }
		        },
		
		        credits: {
		            enabled: false
		        },
		
		        series: [{
		            name: 'DISK',
		            data: [$scope.sysInfo.disk],
		            dataLabels: {
		                format:'<div style="text-align:center"><span style="font-size:20px;color:#3A3C3D;">{y}</span><br/>' +
		                       '<span style="font-size:18px;color:#4F5558">%</span></div>'
		            },
		            tooltip: {
		                valueSuffix: ' %'
		            }
		        }]
		
		    }));
		    
		
		}).error(function(){
			alert(SECONE_ERROR);
		}); 

	}
	
}])
;