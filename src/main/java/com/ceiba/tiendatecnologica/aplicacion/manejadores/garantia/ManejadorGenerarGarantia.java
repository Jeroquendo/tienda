package com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia;

import com.ceiba.tiendatecnologica.aplicacion.fabrica.FabricaGarantia;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.servicio.garantia.ServicioGenerarGarantia;
import com.ceiba.tiendatecnologica.dominio.servicio.producto.ServicioObtenerProducto;
import com.ceiba.tiendatecnologica.dominio.servicio.vendedor.ServicioVendedor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorGenerarGarantia {

	/*private final ServicioObtenerProducto servicioObtenerProducto;
	private final FabricaGarantia fabricaGarantia;
	private final ServicioGenerarGarantia servicioGenerarGarantia;*/

	private final ServicioVendedor servicioVendedor;


	/*public ManejadorGenerarGarantia(ServicioObtenerProducto servicioObtenerProducto, FabricaGarantia fabricaGarantia ,
									ServicioGenerarGarantia servicioGenerarGarantia) {
		this.servicioObtenerProducto = servicioObtenerProducto;
		this.fabricaGarantia = fabricaGarantia;
		this.servicioGenerarGarantia = servicioGenerarGarantia;
	}*/
	public ManejadorGenerarGarantia(ServicioVendedor servicioVendedor) {
		this.servicioVendedor = servicioVendedor;
	}

	@Transactional
	public void ejecutar(String codigoProducto,String nombreCliente) {
		servicioVendedor.generarGarantia(codigoProducto,nombreCliente);
		/*Producto producto = this.servicioObtenerProducto.ejecutar(codigoProducto);
		GarantiaExtendida garantiaExtendida = this.fabricaGarantia.crearGarantiaExtendida(producto);
		this.servicioGenerarGarantia.ejecutar(garantiaExtendida);*/
	}
}
