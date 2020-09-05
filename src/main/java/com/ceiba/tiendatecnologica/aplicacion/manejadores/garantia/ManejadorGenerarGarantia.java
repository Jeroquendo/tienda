package com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia;

import com.ceiba.tiendatecnologica.dominio.servicio.vendedor.ServicioVendedor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorGenerarGarantia {

	private final ServicioVendedor servicioVendedor;

	public ManejadorGenerarGarantia(ServicioVendedor servicioVendedor) {
		this.servicioVendedor = servicioVendedor;
	}

	@Transactional
	public void ejecutar(String codigoProducto,String nombreCliente) {
		this.servicioVendedor.generarGarantia(codigoProducto,nombreCliente);
	}
}
