package com.ceiba.tiendatecnologica.dominio.servicio.producto;


import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import org.springframework.stereotype.Component;

@Component
public class ServicioCrearProducto {

	private RepositorioProducto repositorioProducto;

	public ServicioCrearProducto(RepositorioProducto repositorioProducto) {
		this.repositorioProducto = repositorioProducto;
	}

	public void ejecutar(Producto producto) {
		this.repositorioProducto.agregar(producto);
	}
}
