package com.ceiba.tiendatecnologica.aplicacion.manejadores.producto;

import com.ceiba.tiendatecnologica.aplicacion.comando.ComandoProducto;
import com.ceiba.tiendatecnologica.aplicacion.fabrica.FabricaProducto;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.servicio.producto.ServicioCrearProducto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorCrearProducto {

	private final ServicioCrearProducto servicioCrearProducto;
	private final FabricaProducto fabricaProducto;

	public ManejadorCrearProducto(ServicioCrearProducto servicioCrearProducto, FabricaProducto fabricaProducto) {
		this.servicioCrearProducto = servicioCrearProducto;
		this.fabricaProducto = fabricaProducto;
	}

	@Transactional
	public void ejecutar(ComandoProducto comandoProducto) {
		Producto producto = this.fabricaProducto.crearProducto(comandoProducto);
		this.servicioCrearProducto.ejecutar(producto);
	}
}
