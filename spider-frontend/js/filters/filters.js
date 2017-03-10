'use strict';

/* Filters */

//以下是appBase里面的过滤器，一些数据库原始字段类型转换

angular.module('app.filters', [])
    .filter('time', function() {
        var statusFilter = function(input) {
            var value = "--";
            if (input != null && input != '' && input != 'undefined') {
                value = new Date(input).Format("yyyy/MM/dd hh:mm:ss");
            }
            return value;
        }
        return statusFilter;
    }).filter('shortTime', function() {
        var statusFilter = function(input) {
            var value = "--";
            if (input != null && input != '' && input != 'undefined') {
                value = new Date(input).Format("MM/dd hh:mm");
            }
            return value;
        }
        return statusFilter;
    }).filter('spiderTaskStatus', function() {
        var statusFilter = function(input) {
            var value = input;
            if(input == -1){
                value = '停止';
            }else if(input == 0){
                value = '运行中';
            }
            return value;
        }
        return statusFilter;
    });