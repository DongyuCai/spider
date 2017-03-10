'use strict';

angular.module('MainApp', [])
	.controller('spiderCtrl',['$rootScope','$scope', '$http', 'spiderService','toaster','$timeout', 
		function($rootScope,$scope, $http, spiderService, toaster,$timeout) {
		/*
		var time = $timeout(function() {
			console.log(time);
		}, 1000);
		*/

		$scope.taskList = [];

		$scope.showCreateDialog = function(){
			easyDialog.open({
			  container : 'dialog',
			  fixed : false
			});
		}

		$scope.closeDialog = function(){
			easyDialog.close();
			$("#saveBtn").removeAttr("disabled");
			$scope.task = new Object();
		}

		$scope.task = new Object();
		$scope.saveTask = function(){
			$("#saveBtn").attr("disabled","disabled");

			$scope.task.id = $scope.task.wangZhan+'-'+$scope.task.page;
			$scope.task.status = -1;
			$scope.taskList.push($scope.task);
			$scope.startUp($scope.task);
			$scope.closeDialog();
		}

		$scope.timerFactory = function(task){
			$timeout(function() {
				// console.log(task.wangZhan+'-'+task.page);
				//请求后台接口
				spiderService.analyze(task.wangZhan,task.page,task.id).then(function(taskId){
					for(var i=0;i<$scope.taskList.length;i++){
						var taskContinue = $scope.taskList[i];
						if(taskContinue.id === taskId){
							if(!taskContinue.count){
								taskContinue.count = 0;
							}
							taskContinue.count++;
							if(taskContinue.status === 0){
								$scope.timerFactory(taskContinue);	
							}
						}
					}
				});
			}, 1000);
		}

		$scope.delete = function(task){
			$scope.shutDown(task);
			var index = -1;
			for(var i=0;i<$scope.taskList.length;i++){
				var tmpTask = $scope.taskList[i];
				if(tmpTask.id === task.id){
					index = i;
					break;
				}
			}
			if(index >= 0){
				$scope.taskList.splice(index,1);
			}
		}

		$scope.shutDown = function(task){
			task.status = -1;
		}

		$scope.startUp = function(task){
			if(task.status === -1){
				task.status = 0;//正常状态
				$scope.timerFactory(task);
			}
		}


	}]).factory('spiderService', ['baseRestService', function(baseRestService) {


		var service = {};

		var _service = baseRestService.all('spider');

		service.analyze = function(wangZhan,page,id) {
			return _service.get('analyze/'+wangZhan, {
				page: page,
				id:id
			});
		};

		return service;
	}]);