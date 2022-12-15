package com.formacion.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacion.apirest.entity.Producto;
import com.formacion.apirest.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository repositorio;

	@Override
	public List<Producto> mostrarProductos() {
		// TODO Auto-generated method stub
		return (List<Producto>) repositorio.findAll();
	}

	@Override
	public Producto buscarProducto(long id) {
		// TODO Auto-generated method stub
		return repositorio.findById(id).orElse(null);
	}

	@Override
	public Producto guardarProducto(Producto producto) {
		// TODO Auto-generated method stub
		return repositorio.save(producto);
	}

	@Override
	public Producto borrarProducto(long id) {
		// TODO Auto-generated method stub
		Producto productoBorrado = buscarProducto(id);
		repositorio.deleteById(id);
		return productoBorrado;
	}

}
