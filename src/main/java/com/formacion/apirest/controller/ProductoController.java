package com.formacion.apirest.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.apirest.entity.Cliente;
import com.formacion.apirest.entity.Producto;
import com.formacion.apirest.service.ProductoService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins="http://localhost:4200/")
public class ProductoController {
	
	@Autowired
	private ProductoService servicio;
	
	@GetMapping("productos")
	public List<Producto> index(){
		
		return servicio.mostrarProductos();
		
	}
	
	@GetMapping("productos/{id}")
	public ResponseEntity<?> show(@PathVariable long id) {
		
		Producto producto;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			producto = servicio.buscarProducto(id);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(Objects.isNull(producto)) {
			
			response.put("mensaje", "El producto con ID: "+id+" no existe");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@PostMapping("productos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@RequestBody Producto producto) {
		
		Map<String,Object> response = new HashMap<>();
		Producto nuevoProducto;
		
		try {
			
			nuevoProducto = servicio.guardarProducto(producto);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar insercion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido creado correctamente");
		response.put("cliente", nuevoProducto);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("productos/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Producto producto) {
		
		Producto aux;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			aux = servicio.buscarProducto(id);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(Objects.nonNull(aux)) {
			
			aux.setNombre(producto.getNombre());
			
			
		}else {
			response.put("mensaje", "Error: no se puede editar, el producto con ID: "+id+" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		
		try {
			
			aux=servicio.guardarProducto(aux);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido modificado correctamente");
		response.put("producto", aux);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("productos/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {

		Map<String,Object> response = new HashMap<>();
		Producto productoBorrado;
		
		try {
			
			productoBorrado = servicio.borrarProducto(id);
			
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar borrado en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido borrado correctamente");
		response.put("producto", productoBorrado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}

}
