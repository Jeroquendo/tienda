package com.ceiba.tiendatecnologica.infraestructura.persistencia.builder;

import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.entidad.ProductoEntity;

public class ProductoBuilder {
	
	private ProductoBuilder() {}
	
	public static Producto convertirADominio(ProductoEntity productoEntity) {
		
		Producto producto = null;
		
		if(productoEntity != null) {
			producto = new Producto(productoEntity.getId(),productoEntity.getCodigo(), productoEntity.getNombre(), productoEntity.getPrecio());
		}
		
		return producto;
	}
	
	public static ProductoEntity convertirAEntity(Producto producto) {
		
		ProductoEntity productoEntity = new ProductoEntity();
		productoEntity.setId(producto.getId());
		productoEntity.setCodigo(producto.getCodigo());
		productoEntity.setNombre(producto.getNombre());
		productoEntity.setPrecio(producto.getPrecio());
		
		return productoEntity;
	}
}
