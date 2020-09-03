package com.ceiba.tiendatecnologica.dominio.unitaria;

import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.testdatabuilder.ProductoTestDataBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductoTest {

	private static final String CODIGO = "S01H1AT51";
	private static final String NOMBRE_PRODUCTO = "Impresora";
	private static final int PRECIO = 550000;

	@Test
	public void crearProductoTest() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder().
				conNombre(NOMBRE_PRODUCTO).
				conCodigo(CODIGO).
				conPrecio(PRECIO);

		// act
		Producto producto = productoTestDataBuilder.build();

		// assert
		assertEquals(NOMBRE_PRODUCTO, producto.getNombre());
		assertEquals(CODIGO, producto.getCodigo());
		assertEquals(PRECIO, producto.getPrecio(),0);
	}

}
