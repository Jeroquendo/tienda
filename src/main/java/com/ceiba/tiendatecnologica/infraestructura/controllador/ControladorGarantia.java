package com.ceiba.tiendatecnologica.infraestructura.controllador;

import com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia.ManejadorGenerarGarantia;
import com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia.ManejadorObtenerGarantia;
import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garantia")
public class ControladorGarantia {
	private final ManejadorObtenerGarantia manejadorObtenerGarantia;
	private final ManejadorGenerarGarantia manejadorGenerarGarantia;

	public ControladorGarantia(ManejadorObtenerGarantia manejadorObtenerGarantia, ManejadorGenerarGarantia manejadorGenerarGarantia) {
		this.manejadorObtenerGarantia = manejadorObtenerGarantia;
		this.manejadorGenerarGarantia = manejadorGenerarGarantia;
	}

	@PostMapping("/{idProducto}/{nombreCliente}")
	public void generar(@PathVariable(name = "idProducto") String codigoProducto, @PathVariable(name = "nombreCliente") String nombreCliente) {

		this.manejadorGenerarGarantia.ejecutar(codigoProducto,nombreCliente);
	}

	@GetMapping("/{id}")
	public GarantiaExtendida buscar(@PathVariable(name = "id") String codigo) {
		return this.manejadorObtenerGarantia.ejecutar(codigo);
	}
}
