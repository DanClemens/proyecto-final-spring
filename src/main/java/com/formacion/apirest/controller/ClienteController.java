package com.formacion.apirest.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacion.apirest.entity.Cliente;
import com.formacion.apirest.entity.Region;
import com.formacion.apirest.service.ClienteService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins="http://localhost:4200/")
public class ClienteController {

	@Autowired
	private ClienteService servicio;
	
	@GetMapping("clientes")
	public List<Cliente> index(){
		
		return servicio.mostrarClientes();
		
	}
	
	/*@GetMapping("clientes/{id}")
	public Cliente show(@PathVariable long id) {
		return servicio.buscarCliente(id);
	}*/
	
	@GetMapping("clientes/{id}")
	public ResponseEntity<?> show(@PathVariable long id) {
		
		Cliente cliente;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			cliente = servicio.buscarCliente(id);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(Objects.isNull(cliente)) {
			
			response.put("mensaje", "El cliente con ID: "+id+" no existe");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@GetMapping("clientes/email/{email}")
	public ResponseEntity<?> showByEmail(@PathVariable String email) {
		
		Cliente cliente;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			cliente = servicio.buscarClienteEmail(email);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(Objects.isNull(cliente)) {
			
			response.put("mensaje", "El cliente con email: "+email+" no existe");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	/*@PostMapping("clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody Cliente cliente) {
		return servicio.guardarCliente(cliente);
	}*/
	
	@PostMapping("clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@RequestBody Cliente cliente) {
		
		Map<String,Object> response = new HashMap<>();
		Cliente nuevoCliente;
		
		try {
			
			nuevoCliente = servicio.guardarCliente(cliente);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar insercion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado correctamente");
		response.put("cliente", nuevoCliente);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	/*@PutMapping("clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@PathVariable long id, @RequestBody Cliente cliente) {
		Cliente aux = servicio.buscarCliente(id);
		
		aux.setNombre(cliente.getNombre());
		aux.setApellido(cliente.getApellido());
		aux.setEmail(cliente.getEmail());
		aux.setTelefono(cliente.getTelefono());
		aux.setCreateAt(cliente.getCreateAt());
		
		return servicio.guardarCliente(aux);
		
	}*/
	
	@PutMapping("clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Cliente cliente) {
		
		Cliente aux;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			aux = servicio.buscarCliente(id);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(Objects.nonNull(aux)) {
			
			aux.setNombre(cliente.getNombre());
			aux.setApellido(cliente.getApellido());
			aux.setEmail(cliente.getEmail());
			aux.setTelefono(cliente.getTelefono());
			aux.setCreateAt(cliente.getCreateAt());
			aux.setImagen(cliente.getImagen());
			aux.setRegion(cliente.getRegion());
			
			
		}else {
			response.put("mensaje", "Error: no se puede editar, el cliente con ID: "+id+" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		
		try {
			
			aux=servicio.guardarCliente(aux);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido modificado correctamente");
		response.put("cliente", aux);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		
	}
	
	/*@DeleteMapping("clientes/{id}")
	public Cliente delete(@PathVariable long id) {
		return servicio.borrarCliente(id);
	}*/
	
	@DeleteMapping("clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {

		Map<String,Object> response = new HashMap<>();
		Cliente clienteBorrado;
		
		try {
			
			clienteBorrado = servicio.borrarCliente(id);
			
			String nombreImgAnt = clienteBorrado.getImagen();
			
			if(!Objects.isNull(nombreImgAnt) && (nombreImgAnt.length()>0)) {
				
				Path rutaArchivoAnt = Paths.get("uploads").resolve(nombreImgAnt).toAbsolutePath();//crear la ruta del archivo
				File archivoAnt = rutaArchivoAnt.toFile();
				
				if(archivoAnt.exists() && archivoAnt.canRead()) {
					archivoAnt.delete();
				}
			}
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar borrado en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido borrado correctamente");
		response.put("cliente", clienteBorrado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("clientes/uploads")
	public ResponseEntity<?> uploadImage(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") long id){
		
		Map<String, Object> response= new HashMap<>();
		Cliente cliente;
		String nombreArchivo;
		Path rutaArchivo;
		
		try {
			
			cliente = servicio.buscarCliente(id);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(Objects.isNull(cliente)) {
			response.put("mensaje", "Error: no se puede editar, el cliente con ID: "+id+" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		if(!archivo.isEmpty()) {
			
			String nombreImgAnt = cliente.getImagen();
			
			if(!Objects.isNull(nombreImgAnt) && (nombreImgAnt.length()>0)) {
				
				Path rutaArchivoAnt = Paths.get("uploads").resolve(nombreImgAnt).toAbsolutePath();//crear la ruta del archivo
				File archivoAnt = rutaArchivoAnt.toFile();
				
				if(archivoAnt.exists() && archivoAnt.canRead()) {
					archivoAnt.delete();
				}
			}
			
			
			nombreArchivo = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "_");
			rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();//crear la ruta del archivo
		
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			cliente.setImagen(nombreArchivo);
			
		}
		
		try {
			
			cliente = servicio.guardarCliente(cliente);
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar insercion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado correctamente");
		response.put("cliente", cliente);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	@GetMapping("clientes/img/{nombreImagen:.+}")
	public ResponseEntity<?> showImage(@PathVariable String nombreImagen){
		
		 Path rutaArchivo = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
	     Resource recurso = null;
	        
	     try {
	         recurso = new UrlResource(rutaArchivo.toUri());
	     } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
	        
	        if( !recurso.exists() && !recurso.isReadable() ) {
	            throw new RuntimeException("Error: no se puede cargar la imagen "+nombreImagen);
	        }
	        
	        HttpHeaders cabecera = new HttpHeaders();
	        cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+recurso.getFilename()+"\"");
	        
	        
	        return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
	
	@GetMapping("clientes/regiones")
	public ResponseEntity<?> showRegions() {
		
		List<Region> regiones;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			regiones = servicio.mostrarRegiones();
			
		} catch (DataAccessException e) {
			//si hay error tipo excepcion
			response.put("mensaje", "Error al realizar la consulta a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(Objects.isNull(regiones)) {
			
			response.put("mensaje", "No existen regiones");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Region>>(regiones, HttpStatus.OK);
	}
	
}
