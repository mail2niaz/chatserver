 var app = angular.module('app', ['ngMaterial']);


app.controller('loginCtrl', function ($scope, $log, $mdToast){
	
	$scope.loginValidate = function(){
		window.location.assign('/pages');
	}
})