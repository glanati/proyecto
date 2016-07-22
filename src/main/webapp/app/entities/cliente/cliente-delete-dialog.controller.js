(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ClienteDeleteController',ClienteDeleteController);

    ClienteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cliente'];

    function ClienteDeleteController($uibModalInstance, entity, Cliente) {
        var vm = this;

        vm.cliente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cliente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
