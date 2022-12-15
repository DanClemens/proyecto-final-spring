package com.formacion.apirest.service;

import java.util.List;

import com.formacion.apirest.entity.Cliente;
import com.formacion.apirest.entity.Region;

public interface ClienteService {

	//mostrar todos los clientes
	public List<Cliente> mostrarClientes();
	
	//devuelve cliente por id
	public Cliente buscarCliente(long id);
	
	//crear nuevo cliente
	public Cliente guardarCliente(Cliente cliente);
	
	//borrar cliente
	public Cliente borrarCliente(long id);
	
	//buscar cliente por email
	public Cliente buscarClienteEmail(String email);
	
	//mostrar regiones
	public List<Region> mostrarRegiones();
	
}
