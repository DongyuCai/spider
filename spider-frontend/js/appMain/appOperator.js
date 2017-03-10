'use strict';
angular.module('MainApp', ['ui.router',
	'oc.lazyLoad',
	'ui.bootstrap',
	'app.core',
	'app.directives',
	'app.services',
	'app.utils',
	'app.filters',
	'app.settings',
	'restangular',
	'LocalStorageModule',
	'toaster',
	'ngAnimate',
	'w5c.validator'
]);


//  注册组件
var mainApp = angular.module('MainApp')
	.config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide', function($controllerProvider, $compileProvider, $filterProvider, $provide) {
		// lazy controller, directive and service
		mainApp.controller = $controllerProvider.register;
		mainApp.directive = $compileProvider.directive;
		mainApp.filter = $filterProvider.register;
		mainApp.factory = $provide.factory;
		mainApp.service = $provide.service;
		mainApp.constant = $provide.constant;
		mainApp.value = $provide.value;
	}]);

mainApp
	.run(
		['$rootScope', '$state', '$stateParams', 'localStorageService',
			function($rootScope, $state, $stateParams, localStorageService) {

				$rootScope.$state = $state;
				$rootScope.$stateParams = $stateParams;
			}
		]
	)
	.config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
		function($stateProvider, $urlRouterProvider, $httpProvider) {
			$urlRouterProvider.otherwise('/spider');
			$stateProvider
			// 主页路由
			.state('spider', {
				url: '/spider',
				templateUrl: 'home/spider.html',
				controller: 'spiderCtrl',
				resolve: {
					deps: ['$ocLazyLoad',
						function($ocLazyLoad) {
							return $ocLazyLoad.load([]).then(
								function() {
									return $ocLazyLoad.load(['home/spider.js']);
								}
							);
						}
					]
				}

			});
			$httpProvider.interceptors.push('interceptor');
		}
	])
	.run(['$rootScope', '$state', '$stateParams', '$window' , '$templateCache', function($rootScope, $state, $stateParams, $window, $templateCache) {
		//禁止angular 模板缓存
		$rootScope.$on('$routeChangeStart', function(event, next, current) {  
	        if (typeof(current) !== 'undefined'){  
	            $templateCache.remove(current.templateUrl);  
	        }  
	    });  
		$rootScope.$on('$stateChangeSuccess', function() {
			$window.scrollTo(0, 0); //跳转成功 初始化在顶部
		});
	}])
	.factory('interceptor', ['$q', '$rootScope', 'toaster', '$timeout','localStorageService',function($q, $rootScope, toaster, $timeout,localStorageService) {

		var interceptor = {
			'request': function(config) {
				//config.headers['X-1008-Application-Id'] = 'web';
				//if ($rootScope.$token != null && $rootScope.$token != 'undefined') {
				//    config.headers['X-1008-Session-Token'] = $rootScope.$token;
				//}

				return config;
			},
			'response': function(response) {

				return response;
			},
			'responseError': function(rejection) {

				switch (rejection.status) {
					case 0:
						toaster.pop('error', "0错误", '服务器连接失败！', 5000, 'trustedHtml');
						break;
					case 400:
						var message = rejection.data;
						if(message){
							toaster.pop('error', "400错误", '' + message + '', 5000, 'trustedHtml');
						} else {
							toaster.pop('error', "400错误", '' + rejection.config.url + '请求失败！参数异常', 5000, 'trustedHtml');
						}
						break;
					case 401:
						var message = rejection.data;
						toaster.pop('error', "401错误", '' + message + '', 5000, 'trustedHtml');
						break;
					case 403:
						toaster.pop('error', "403错误", '' + rejection.data + '', 5000, 'trustedHtml');
						break;
					case 404:
						toaster.pop('error', "404错误", '' + rejection.config.url + '请求失败！', 5000, 'trustedHtml');
						break;
					case 500:
						var message = rejection.data;
						toaster.pop('error', "500错误", '服务器500错误!', 5000, 'trustedHtml');
						break;
					case 800:
						var message = rejection.data;
						toaster.pop('error', "执行失败", '' + message + '', 5000, 'trustedHtml');
						break;
				}
				return $q.reject(rejection);
			}
		}

		return interceptor;
	}])
	.factory('baseRestService', ['Restangular', '$rootScope', 'toaster',
		function(Restangular, $rootScope, toaster) {

			Restangular.setRequestInterceptor(
				function(element, operation, what, url) {
					if (operation == 'getList') {
						// maya.notice.wait("");
					} else if (operation == 'get') {


					}
					return element;
				}
			);

			Restangular.setResponseInterceptor(
				function(data, operation, what) {


					if (operation == 'getList') {
						// maya.notice.close();
						//console.info(what);
						var list = data[what + "s"];
						if (list) {
							list.totalpage = data['totalpage'];
						}
						return list;
					} else if (operation == 'post' || operation == 'put' || operation == 'remove') {
						// maya.notice.close();
						if (data['message']) {
							toaster.pop('success', "提示", '' + data['message'] + '', 5000, 'trustedHtml');
						} else {
							toaster.pop('success', "提示", '执行成功', 5000, 'trustedHtml');
						}

					} else if (operation == 'get') {

					}
					return data;
				}
			);

			return Restangular.withConfig(function(Configurer) {
				Configurer.setBaseUrl(API_BASE_DOMAIN);
			});
		}
	]);