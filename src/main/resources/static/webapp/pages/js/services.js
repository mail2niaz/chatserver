var appServices = angular.module('appServices',['ngResource']); 

//var apiLink = 'http://52.38.38.95:8080/SpringRestfulWeb',globalTimeout=30000;
var apiLink = 'http://52.53.224.43:8080/SpringRestfulWeb',globalTimeout=30000;
//var apiLink = 'http://192.168.15.141:8080/SpringRestfulWeb',globalTimeout=300000;

appServices.factory('ftp',function($resource){
    return {
        info: $resource(apiLink+'/auditList', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      })
    };
});

appServices.factory('users',function($resource){
    return {
      add_user: $resource(apiLink+'/createUser', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
      info: $resource(apiLink+'/userList', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
      del_user: $resource(apiLink+'/', {}, {
        query: {method: 'DELETE', params: {}, isArray: false, timeout: globalTimeout}
      }),
    };
});

appServices.factory('hdd', function ($resource) {
  return {
    hdd_data: $resource(apiLink+'/hdDataList', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    })
  }
});

appServices.factory('orders', function ($resource) {
  return {
    info: $resource(apiLink+'/orderList', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    }),
    update_date: $resource(apiLink+'/updateLastDownloadDate', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    })
  }
});

appServices.factory('search', function ($resource) {
  return {
    data_fields: $resource(apiLink+'/getSearchRules', {}, {
      query: {method: 'GET', params: {}, isArray: false, timeout: globalTimeout}
    }),
    advanced_search: $resource(apiLink+'/advanceSearch', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    }),
    dQuery_list: $resource(apiLink+'/getOrderList', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
    save_query: $resource(apiLink+'/saveOrders', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    }),
    advanced_search_count: $resource(apiLink+'/getSearchCountLst', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    }),
    dropdown_list: $resource(apiLink+'/getDropdownLst', {}, {
      query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
    })
  }
});

appServices.factory('settings',function($resource){
    return {
      cust_info: $resource(apiLink+'/customerList', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
      add_cust: $resource(apiLink+'/createCustomer', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
      del_cust: $resource(apiLink+'/', {}, {
        query: {method: 'DELETE', params: {}, isArray: false, timeout: globalTimeout}
      }),
      comp_info: $resource(apiLink+'/companyList', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
      add_comp: $resource(apiLink+'/createCompany', {}, {
        query: {method: 'POST', params: {}, isArray: false, timeout: globalTimeout}
      }),
      del_comp: $resource(apiLink+'/', {}, {
        query: {method: 'DELETE', params: {}, isArray: false, timeout: globalTimeout}
      }),
    };
});
