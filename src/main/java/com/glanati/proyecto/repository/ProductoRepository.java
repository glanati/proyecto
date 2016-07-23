package com.glanati.proyecto.repository;

import com.glanati.proyecto.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Producto entity.
 */
@SuppressWarnings("unused")
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    List<Producto> findAllByCliente_Id(Long clienteId);
}
