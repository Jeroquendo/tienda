package com.ceiba.tiendatecnologica.dominio.servicio.vendedor;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.excepcion.GarantiaExtendidaException;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.Date;

public class ServicioVendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantía extendida";
	public static final String EL_PRODUCTO_NO_TIENE_GARANTIA = "Este producto no cuenta con garantía extendida";
	public static final int CANTIDAD_MAXIMA_VOCALES = 3;
	public static final double PRECIO_MINIMO_PARA_GARANTIA = 500000;
	public static final double PORCETAJE_DESCUENTO_GARANTIA_MAXIMO = 0.2;
	public static final double PORCETAJE_DESCUENTO_GARANTIA_MINIMO = 0.1;
	public static final int CANTIDAD_MAXIMA_GARANTIA = 200;
	public static final int CANTIDAD_MINIMA_GARANTIA = 100;

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	public ServicioVendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;
	}

	public void generarGarantia(String codigo, String nombreCliente) {
		if (!tieneGarantia(codigo)){
			validarCodigoProducto(codigo);
			Date fechaIncioGarantia = new Date();
			Pair<Double,Date> tuplaPrecioFecha = calcularPrecioGarantiaYFecha(codigo,fechaIncioGarantia);
			double precioFinal = tuplaPrecioFecha.getKey();
			Date fechaFinGarantia = tuplaPrecioFecha.getValue();
			ingresarGarantia(codigo, nombreCliente,precioFinal,fechaFinGarantia);
		}else{
			lanzarExcepcion(EL_PRODUCTO_TIENE_GARANTIA);
		}
	}

	public void lanzarExcepcion(String texto){
		throw new GarantiaExtendidaException(texto);
	}

	public void ingresarGarantia(String codigoProducto, String nombreCliente, double precioFinalProducto, Date fechaFinGarantia){
		Producto productoExistente = repositorioProducto.obtenerPorCodigo(codigoProducto);
		Date fechaSolicitudGarantia = new Date();

		repositorioGarantia.agregar(
				new GarantiaExtendida(productoExistente,fechaSolicitudGarantia,
						fechaFinGarantia,precioFinalProducto,nombreCliente)
		);

	}

	public boolean tieneGarantia(String codigo) {
		return repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo)!= null;
	}

	public boolean codigoValidoParaGenerarGarantia(String codigo) {
		return contarVocalesCodigoProducto(codigo) != CANTIDAD_MAXIMA_VOCALES;
	}

	public void validarCodigoProducto(String codigoProducto) {
		if (!codigoValidoParaGenerarGarantia(codigoProducto)) {
			lanzarExcepcion(EL_PRODUCTO_NO_TIENE_GARANTIA);
		}
	}

	private boolean caracterEsVocal(char letra) {
		return "aeiou".contains(String.valueOf(letra).toLowerCase());
	}

	private int contarVocalesCodigoProducto(String codigo) {
		int cantidadVocales = 0;
		for (int iterador= 0; iterador < codigo.length(); iterador++){
			if(caracterEsVocal(codigo.charAt(iterador))) cantidadVocales ++;
		}
		return cantidadVocales;
	}

	public Pair<Double, Date> calcularPrecioGarantiaYFecha(String codigo, Date fechaInicio) {

		Double precioProducto = repositorioProducto.obtenerPorCodigo(codigo).getPrecio();
		Double valorPrecioPoductoDescuento;
		Date fechaFinGarantia;
		if(precioProducto > PRECIO_MINIMO_PARA_GARANTIA){
			valorPrecioPoductoDescuento = precioProducto * PORCETAJE_DESCUENTO_GARANTIA_MAXIMO;
			fechaFinGarantia = calcularFechaFinGarantia(PORCETAJE_DESCUENTO_GARANTIA_MAXIMO, fechaInicio);
		}
		else{
			valorPrecioPoductoDescuento = precioProducto * PORCETAJE_DESCUENTO_GARANTIA_MINIMO;
			fechaFinGarantia = calcularFechaFinGarantia(PORCETAJE_DESCUENTO_GARANTIA_MINIMO, fechaInicio);
		}
		return new Pair<> (valorPrecioPoductoDescuento,fechaFinGarantia);
	}


	public Date calcularFechaFinGarantia(Double porcentajeDescuento , Date fechaInicioGarantia){

		Date fechaLimiteGarantia;
		if (Double.compare(PORCETAJE_DESCUENTO_GARANTIA_MAXIMO, porcentajeDescuento) == 0) {
			fechaLimiteGarantia = retornarFechaDiasHabiles(CANTIDAD_MAXIMA_GARANTIA, fechaInicioGarantia);
		} else {
			fechaLimiteGarantia = retornarFechaDiasHabiles(CANTIDAD_MINIMA_GARANTIA, fechaInicioGarantia);
		}
		return fechaLimiteGarantia;
	}

	public Date retornarFechaDiasHabiles(int cantidadDiasGarantia, Date fechaInicioGarantia) {

		Calendar calendarioActual = Calendar.getInstance();
		calendarioActual.setTime(fechaInicioGarantia);
		int diaActual = calendarioActual.get(Calendar.DAY_OF_WEEK);
		int contadorDias = 1;

		while(contadorDias < cantidadDiasGarantia){
			calendarioActual.add(Calendar.DATE, 1);
			diaActual = calendarioActual.get(Calendar.DAY_OF_WEEK);
			if(diaActual != Calendar.MONDAY) contadorDias++;
		}

		if (diaActual == Calendar.SUNDAY) calendarioActual.add(Calendar.DATE, 2);
		return new Date(calendarioActual.getTimeInMillis());
	}
}
