package com.formacion.apirest.service;

import java.util.List;

import com.formacion.apirest.entity.Producto;


public interface ProductoService {
	
	//mostrar todos los clientes
		public List<Producto> mostrarProductos();
		
		//devuelve cliente por id
		public Producto buscarProducto(long id);
		
		//crear nuevo cliente
		public Producto guardarProducto(Producto producto);
		
		//borrar cliente
		public Producto borrarProducto(long id);

}
