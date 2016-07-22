package com.glanati.proyecto.web.rest.mapper;

import com.glanati.proyecto.domain.Cliente;
import com.glanati.proyecto.web.rest.dto.ClienteDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClienteMapper {

    ClienteDTO clienteToClienteDTO(Cliente cliente);

    List<ClienteDTO> clientesToClienteDTOs(List<Cliente> clientes);

    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

    List<Cliente> clienteDTOsToClientes(List<ClienteDTO> clienteDTOs);
}
