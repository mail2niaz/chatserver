 var app = angular.module('app', ['ngRoute',  'ngMaterial']);

 app.config(['$routeProvider',
     function($routeProvider, $location) {
         $routeProvider
             .when('/', {
                 templateUrl: 'dashboard.html',
                 controller: 'dashboardCtrl'
             })             
             .when('/dashboard', {
                 templateUrl: 'dashboard.html',
                 controller: 'dashboardCtrl'
             })
             .when('/otherwise', {
                 templateUrl: 'error.html',
             })
     }

 ]);

 app.controller('userListCtrl', function($rootScope, $scope, $log) { 
	    var imagePath = '../../assets/usericon.jpg';
	    $scope.phones = [
	      { type: 'Home', number: '(555) 251-1234' },
	      { type: 'Cell', number: '(555) 786-9841' },
	      { type: 'Office', number: '(555) 314-1592' }
	    ];
	    $scope.todos = [
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	    ];
	});

 app.controller('chatListCtrl', function($rootScope, $scope, $log) { 
	    var imagePath = '../../assets/usericon.jpg';
	    $scope.phones = [
	      { type: 'Home', number: '(555) 251-1234' },
	      { type: 'Cell', number: '(555) 786-9841' },
	      { type: 'Office', number: '(555) 314-1592' }
	    ];
	    $scope.todos = [
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	      {
	        face : imagePath,
	        what: 'Brunch this weekend?',
	        who: 'Min Li Chan',
	        when: '3:08PM',
	        notes: " I'll be in your neighborhood doing errands"
	      },
	    ];
	});
 
 app.controller('userSearchCtrl', userSearchCtrl);
 function userSearchCtrl($rootScope, $scope, $log)
 {
	    var self = this;
	    self.simulateQuery = false;
	    self.isDisabled    = false;
	    // list of `state` value/display objects
	    self.states        = loadAll();
	    self.querySearch   = querySearch;
	    self.selectedItemChange = selectedItemChange;
	    self.searchTextChange   = searchTextChange;
	    self.newState = newState;
	    function newState(state) {
	      alert("Sorry! You'll need to create a Constituion for " + state + " first!");
	    }
	    // ******************************
	    // Internal methods
	    // ******************************
	    /**
	     * Search for states... use $timeout to simulate
	     * remote dataservice call.
	     */
	    function querySearch (query) {
	      var results = query ? self.states.filter( createFilterFor(query) ) : self.states,
	          deferred;
	      if (self.simulateQuery) {
	        deferred = $q.defer();
	        $timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
	        return deferred.promise;
	      } else {
	        return results;
	      }
	    }
	    function searchTextChange(text) {
	      $log.info('Text changed to ' + text);
	    }
	    function selectedItemChange(item) {
	      $log.info('Item changed to ' + JSON.stringify(item));
	    }
	    /**
	     * Build `states` list of key/value pairs
	     */
	    function loadAll() {
	      var allStates = 'Alabama, Alaska, Arizona, Arkansas, California, Colorado, Connecticut, Delaware,\
	              Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Louisiana,\
	              Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana,\
	              Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina,\
	              North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina,\
	              South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, West Virginia,\
	              Wisconsin, Wyoming';
	      return allStates.split(/, +/g).map( function (state) {
	        return {
	          value: state.toLowerCase(),
	          display: state
	        };
	      });
	    }
	    /**
	     * Create filter function for a query string
	     */
	    function createFilterFor(query) {
	      var lowercaseQuery = angular.lowercase(query);
	      return function filterFn(state) {
	        return (state.value.indexOf(lowercaseQuery) === 0);
	      };
	    }
	  }
 app.controller("chatSendCtrl",function($rootScope, $scope, $log){});
 app.controller("chatSendIconsCtrl",function($rootScope, $scope, $log){
	 var iconData = [
	                 {name: '../assets/chaticons/Voice'        , color: "#777" },
	                 {name: 'Smile'   , color: "rgb(89, 226, 168)" },
	                 {name: 'image', color: "#A00" },
	                 {name: 'Online'    , color:"#00A" }
	                  // Use theming to color the font-icon
	               ];
	           // Create a set of sizes...
	           $scope.sizes = [
	             {size:48,padding:10},
	             {size:36,padding:6},
	             {size:24,padding:2},
	             {size:12,padding:0}
	           ];
	           $scope.fonts = [].concat(iconData);
	       })
	       .config(function($mdThemingProvider){
	         // Update the theme colors to use themes on font-icons
	         $mdThemingProvider.theme('default')
	               .primaryPalette("red")
	               .accentPalette('green')
	               .warnPalette('blue');
 });