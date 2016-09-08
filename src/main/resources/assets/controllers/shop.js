angular.module('vouchApp', [])
  .controller('VoucherShopController', function($scope, $http) {
	  $scope.stock = [
	               {"name" : "Dziwki",
	            	   "price" : "3000"},
	               {"name" : "Koks",
	            	   "price" : "323"},
	               {"name" : "Lasery",
	            	   "price" : "275"}
	               ];
	  $scope.selected = {};
	  $scope.codes = [];
	  $scope.discountPrice = [];
	  $scope.chooseArticle = function(article, code, index) {
		  $http.get("/voucher/use?code="+code+"&price="+article.price)
		  	.then(function(response) {
		  		$scope.discountPrice[index] = response.data;
		  	});
//		  $scope.selected = article;
//		  $scope.code = code;
	  }
  });