angular.module('admin', [
    'admin.index.controllers',
    'admin.services',
    'admin.filters',
    'admin.directives',
    'ui.router'])
    .run(['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            // 把$state和$stateParams放入$rootScope，方便在任何地方使用。
            // It's very handy to add references to $state and $stateParams to the $rootScope
            // so that you can access them from any scope within your applications.For example,
            // <li ng-class="{ active: $state.includes('contacts.list') }"> will set the <li>
            // to active whenever 'contacts.list' or one of its decendents is active.
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }
    ])
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider) {
            $locationProvider.html5Mode(false).hashPrefix('');
            $urlRouterProvider.otherwise('/');
            $stateProvider
                .state('menuList', {
                    url: '/menuList',
                    templateUrl: "menuList.html",
                    controller: 'menuList-ctrl'
                })
                .state('menuList.menu1', {
                    url: '/menu1',
                    templateUrl: "menu1.html",
                    controller: 'menu1-ctrl'
                })
                .state('menuList.menu2', {
                    url: '/menu2/:id',
                    // url: '/menu2?id',
                    templateUrl: "menu2.html",
                    controller: 'menu2-ctrl'
                })
                .state('user', {
                    url: '/user',
                    templateUrl: "user.html",
                    controller: 'user-ctrl'
                })
                .state('role', {
                    url: '/role',
                    templateUrl: "role.html",
                    controller: 'role-ctrl'
                })
                .state('file', {
                    url: '/file',
                    templateUrl: "grid.html",
                    controller: 'grid-ctrl'
                })
            ;
        }
    ])
;
