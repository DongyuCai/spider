mainApp
  .controller('HeadController', ['$rootScope', '$scope', '$state', 'localStorageService',function($rootScope, $scope, $state, localStorageService){


	    $scope.status = "";
		$scope.selectStatus = function(status){
			$scope.status = status;
			location.href = "#/"+status;
	    };

	    //F5刷新页面初始化
	    $scope.status = 'spider';
	    var words = location.href.split('#/');
	    if(words[1]){
	    	words = words[1].split("/");
	    	$scope.status = words[0];
	    }

  		// var status = $rootScope.$state.current.name;
  		// $scope.selectStatus(status);

  }]);