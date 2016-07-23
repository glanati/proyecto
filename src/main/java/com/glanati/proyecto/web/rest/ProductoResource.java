package com.glanati.proyecto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.glanati.proyecto.domain.Producto;
import com.glanati.proyecto.service.ProductoService;
import com.glanati.proyecto.web.rest.dto.ProductoDTO;
import com.glanati.proyecto.web.rest.mapper.ProductoMapper;
import com.glanati.proyecto.web.rest.util.HeaderUtil;
import com.glanati.proyecto.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    @Inject
    private ProductoService productoService;

    @Inject
    private ProductoMapper productoMapper;

    /**
     * POST  /productos : Create a new producto.
     *
     * @param productoDTO the productoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productoDTO, or with status 400 (Bad Request) if the producto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", productoDTO);
        if (productoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("producto", "idexists", "A new producto cannot already have an ID")).body(null);
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("producto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos : Updates an existing producto.
     *
     * @param productoDTO the productoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productoDTO,
     * or with status 400 (Bad Request) if the productoDTO is not valid,
     * or with status 500 (Internal Server Error) if the productoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductoDTO> updateProducto(@Valid @RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", productoDTO);
        if (productoDTO.getId() == null) {
            return createProducto(productoDTO);
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("producto", productoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos : get all the productos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProductoDTO>> getAllProductos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Productos");
        Page<Producto> page = productoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productos");
        return new ResponseEntity<>(productoMapper.productosToProductoDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the productoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/productos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        ProductoDTO productoDTO = productoService.findOne(id);
        return Optional.ofNullable(productoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productos/:id : delete the "id" producto.
     *
     * @param id the id of the productoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/productos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("producto", id.toString())).build();
    }

    /**
     * GET  /productos/cliente/:clienteId : get the "id" producto by cliente
     *
     */
    @RequestMapping(value = "/productos/cliente/{clienteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProductoDTO>> getProductoByCliente(@PathVariable Long clienteId) {
        log.debug("REST request to get Producto by clliente : {}", clienteId);
        List<Producto> productos = productoService.findAllByCliente(clienteId);
        return new ResponseEntity<>(productoMapper.productosToProductoDTOs(productos), null, HttpStatus.OK);
    }
}
