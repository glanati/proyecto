(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Producto', 'Cliente'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, entity, Producto, Cliente) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
