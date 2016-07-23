package com.glanati.proyecto.service;

import com.glanati.proyecto.domain.Producto;
import com.glanati.proyecto.repository.ProductoRepository;
import com.glanati.proyecto.web.rest.dto.ProductoDTO;
import com.glanati.proyecto.web.rest.mapper.ProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Inject
    private ProductoRepository productoRepository;

    @Inject
    private ProductoMapper productoMapper;

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.productoDTOToProducto(productoDTO);
        producto = productoRepository.save(producto);
        ProductoDTO result = productoMapper.productoToProductoDTO(producto);
        return result;
    }

    /**
     *  Get all the productos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        Page<Producto> result = productoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one producto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProductoDTO findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);
        ProductoDTO productoDTO = productoMapper.productoToProductoDTO(producto);
        return productoDTO;
    }

    /**
     *  Delete the  producto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.delete(id);
    }

    public List<Producto> findAllByCliente(Long clienteId) {
        return productoRepository.findAllByCliente_Id(clienteId);
    }
}
