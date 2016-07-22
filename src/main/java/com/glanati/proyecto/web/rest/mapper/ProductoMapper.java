package com.glanati.proyecto.web.rest.mapper;

import com.glanati.proyecto.domain.Cliente;
import com.glanati.proyecto.domain.Producto;
import com.glanati.proyecto.web.rest.dto.ProductoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Producto and its DTO ProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductoMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    ProductoDTO productoToProductoDTO(Producto producto);

    List<ProductoDTO> productosToProductoDTOs(List<Producto> productos);

    @Mapping(source = "clienteId", target = "cliente")
    Producto productoDTOToProducto(ProductoDTO productoDTO);

    List<Producto> productoDTOsToProductos(List<ProductoDTO> productoDTOs);

    default Cliente clienteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
