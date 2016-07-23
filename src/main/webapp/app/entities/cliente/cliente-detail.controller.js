(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ClienteDetailController', ClienteDetailController);

    ClienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Cliente', 'ProductoByCliente'];

    function ClienteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Cliente, ProductoByCliente) {
        var vm = this;

        vm.cliente = entity;
        vm.productos = [];
        vm.loadProductos = function() {
            ProductoByCliente.query({clienteId: vm.cliente.id}, function(result) {
                vm.productos = result;
                console.log(vm.productos);
            });
        };

        vm.cliente.$promise.then(function() {
           vm.loadProductos();
        });
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoApp:clienteUpdate', function(event, result) {
            vm.cliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
