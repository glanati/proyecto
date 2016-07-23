(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Producto', Producto)
        .factory('ProductoByCliente', ProductoByCliente);

    Producto.$inject = ['$resource'];
    ProductoByCliente.$inject = ['$resource'];

    function Producto ($resource) {
        var resourceUrl =  'api/productos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }

    function ProductoByCliente ($resource) {
        var resourceUrl = 'api/productos/cliente/:clienteId';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
