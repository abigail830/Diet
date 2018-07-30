angular.module('admin.index.controllers', [])
    .controller('index-ctrl', function ($scope, adService, cache) {
        $scope.auth = {};
        adService.getWithCBLoading("../../json/auth.json", null, function(res){
            cache.setObject("auth", res);
            $scope.auth = res;
        }, function(res){
            cache.setObject("auth", res);
            $scope.auth = res;
        })
    })
    .controller('menuList-ctrl', function ($scope, adService, cache) {
        console.log(cache.get("auth"));
    })
    .controller('menu1-ctrl', function ($scope, $state, adService) {
        $scope.list = [];

        adService.getJsonData(function(res){
            $scope.list = res.data.menuTest1;
        });

        $scope.redirect = function (id) {
            $state.go('menuList.menu2', {id: id}, {reload: true});
        }
    })
    .controller('menu2-ctrl', function ($scope, $stateParams, adService) {
        showTip($stateParams.id);
        $scope.save = function(model){
            let cb = function(res){
                alert(JSON.stringify(model));
            };
            adService.saveUser(model, cb)
        }
    })
    .controller('user-ctrl', function ($scope, adService) {
        $scope.save = function(user){
            let cb = function(res){
                alert(JSON.stringify(res));
            };
            adService.saveUser(user, cb)
        }
    })
    .controller('role-ctrl', function ($scope, adService) {
        $scope.roles = [];
        adService.getJsonData(function(res){
            $scope.roles = res.data.roles;
        });
    })
    .controller('file-ctrl', function ($scope, adService) {

    })
;