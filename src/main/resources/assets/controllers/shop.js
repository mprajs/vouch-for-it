angular.module('vouchApp', [])
  .controller('VoucherShopController', function($scope, $http) {
	  $scope.stock = [
	               {"name" : "Szmery",
	            	   "price" : "3000"},
	               {"name" : "Bajery",
	            	   "price" : "323"},
	               {"name" : "Lasery",
	            	   "price" : "275"}
	               ];
	  $scope.selected = {};
	  $scope.codes = [];
	  $scope.discountPrice = [];
	  $scope.chooseArticle = function(article, code, index) {
		  var rq = {"code" : code,
				  "price" : article.price};
		  $http.put("/voucher/use", rq)
		  	.then(function(response) {
		  		$scope.discountPrice[index] = response.data;
		  	});
//		  $scope.selected = article;
//		  $scope.code = code;
	  }
  });