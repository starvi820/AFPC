'use strict';
angular.module('AfpcApp')
	.filter('to_trusted', ['$sce', function($sce){
        return function(text) {
            return $sce.trustAsHtml(text);
        };
    }])
    .filter('truncateA', function() {
        return function(text, length, end) {
        	if (text == '' ) return text;
            if (!angular.isNumber(length)) {
                length = 10;
            }

            if (end === undefined){
                end = '...';
            }

            if (text.length <= length || text.length - end.length <= length) {
                return text;
            }
            else {
                return String(text).substring(0, length - end.length) + end;
            }
        };
    })
    .filter('truncate',  function() {
        return function(text, length) {
            
        	if (text == '' ) return text;
            if (!angular.isNumber(length)) {
                length = 20;
            }
            
            var l = 0;
            for (var i=0; i<text.length; i++) 
            	l += (text.charCodeAt(i) > 128) ? 2 : 1;
            if ( l <= length+1 ) return text;
           
            l=0;
            var clen = length-3;
            for (var i=0; i<text.length; i++) {
            	l += (text.charCodeAt(i) > 128) ? 2 : 1;
            	if(l == clen) return text.substring(0,i+1) + '...';
            	if( l > clen) return text.substring(0,i+1) + '..';
            }
            return text;
        };
    });