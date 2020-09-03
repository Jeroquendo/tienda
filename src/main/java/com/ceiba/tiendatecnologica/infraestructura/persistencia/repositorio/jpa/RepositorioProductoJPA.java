package com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio.jpa;

import com.ceiba.tiendatecnologica.infraestructura.persistencia.entidad.ProductoEntity;

public interface RepositorioProductoJPA {

	/**
	 * Permite obtener un producto entity por un codigo
	 * @param codigo
	 * @return
	 */
	ProductoEntity obtenerProductoEntityPorCodigo(String codigo);

}
