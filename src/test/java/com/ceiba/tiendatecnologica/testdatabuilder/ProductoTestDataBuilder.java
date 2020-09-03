package com.ceiba.tiendatecnologica.testdatabuilder;


import com.ceiba.tiendatecnologica.aplicacion.comando.ComandoProducto;
import com.ceiba.tiendatecnologica.dominio.Producto;

public class ProductoTestDataBuilder {

	private static final String CODIGO= "F01TSA0150";
	private static final String NOMBRE = "Computador Lenovo";
	private static final double PRECIO = 780000;
	
	private String codigo;
	private String nombre;
	private double precio;
	
	public ProductoTestDataBuilder() {
		this.codigo = CODIGO;
		this.nombre = NOMBRE;
		this.precio = PRECIO;
		
	}

	public ProductoTestDataBuilder conCodigo(String cedula) {
		this.codigo=cedula;
		return this;
	}

	public ProductoTestDataBuilder conNombre(String nombre) {
		this.nombre=nombre;
		return this;
	}

	public ProductoTestDataBuilder conPrecio(double costo) {
		this.precio = costo;
		return this;
	}
	
	
	public Producto build() {
		return new Producto(null,this.codigo, this.nombre,this.precio);
	}

	public ComandoProducto buildComando() {
		return new ComandoProducto(this.codigo, this.nombre,this.precio);
	}
}
