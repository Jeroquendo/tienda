package com.ceiba.tiendatecnologica.dominio.repositorio;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;

public interface RepositorioGarantiaExtendida {

	/**
	 * Permite obtener un producto con garantia extendida dado un codigo
	 * @param codigo
	 * @return
	 */
	Producto obtenerProductoConGarantiaPorCodigo(String codigo);
	
	/**
	 * Permite agregar una garantia al repositorio de garantia
	 * @param garantia
	 */
	void agregar(GarantiaExtendida garantia);
	
	/**
	 * Permite obtener una garantia extendida por el codigo del producto
	 * @param codigo
	 */
	GarantiaExtendida obtener(String codigo);

}
