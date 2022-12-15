package com.formacion.apirest.repository;

import org.springframework.data.repository.CrudRepository;

import com.formacion.apirest.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long>{

}
