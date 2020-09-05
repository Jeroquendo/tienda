package com.ceiba.tiendatecnologica.dominio.unitaria;


import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.excepcion.GarantiaExtendidaException;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import com.ceiba.tiendatecnologica.dominio.servicio.vendedor.ServicioVendedor;
import com.ceiba.tiendatecnologica.testdatabuilder.ProductoTestDataBuilder;
import javafx.util.Pair;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioVendedorTest {

	private static final String MENSAJE = "Para retornar excepción el código debe contener tres vocales";
	public static final String EL_PRODUCTO_NO_TIENE_GARANTIA = "Este producto no cuenta con garantía extendida";
	public static final double PORCETAJE_DESCUENTO_GARANTIA_MAXIMO = 0.2;
	public static final double PORCETAJE_DESCUENTO_GARANTIA_MINIMO = 0.1;
	public static final String CODIGO = "AFBT1";

	@Test
	public void productoYaTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		// act 
		boolean existeProducto = servicioVendedor.tieneGarantia(producto.getCodigo());
		
		//assert
		assertTrue(existeProducto);
	}
	
	@Test
	public void productoNoTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		// act 
		boolean existeProducto =  servicioVendedor.tieneGarantia(producto.getCodigo());
		
		//assert
		assertFalse(existeProducto);
	}

	@Test
	public void tieneGarantiaTestDebeRetornarTrueSiYaExisteUnaGarantiaParaElProducto(){

		//arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);

		//act
		boolean resultado = servicioVendedor.tieneGarantia(producto.getCodigo());

		//assert
		assertTrue(resultado);
	}

	@Test
	public void tieneGarantiaTestDebeRetornarFalseSiNoExisteUnaGarantiaParaElProducto(){

		//arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);

		//act
		boolean resultado = servicioVendedor.tieneGarantia(producto.getCodigo());

		//assert
		assertFalse(resultado);
	}

	@Test
	public void validarCodigoDeProductoTestDebeLanzarExcepcionCuandoValidacionDeVocalesVerdadero(){
		//arrange
		String codigoProducto = "ABETPO5";
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		try {
			//act
			servicioVendedor.validarCodigoProducto(codigoProducto);
			fail(MENSAJE);
		}catch (GarantiaExtendidaException e){
			//assert
			assertTrue(e.getMessage().equalsIgnoreCase(EL_PRODUCTO_NO_TIENE_GARANTIA));
		}
	}

	@Test
	public void validarCodigoDeProductoTestNoDebeLanzarExcepcionCuandoValidacionDeVocalesFalsa(){
		//arrange
		String codigoProducto = "ABETP05";
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = Mockito.spy(new ServicioVendedor(repositorioProducto, repositorioGarantia));

		//act
		servicioVendedor.validarCodigoProducto(codigoProducto);

		//assert
		Mockito.verify(servicioVendedor,Mockito.times(0)).lanzarExcepcion(Mockito.anyString());
	}

	@Test
	public void calcularPrecioGarantiaYFechaTestDebeRetornarVeintePorcientoDelPrecio(){

		//arrange
		final Date FECHA_INICIO_GARANTIA = new GregorianCalendar(2020, Calendar.MAY, 14).getTime();
		final Date FECHA_FINAL_GARANTIA = new GregorianCalendar(2020, Calendar.AUGUST, 8).getTime();
		Pair<Double,Date> valorEsperado = new Pair<Double, Date>(120000.0,FECHA_FINAL_GARANTIA) ;
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.conCodigo(CODIGO).conPrecio(600000).build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = Mockito.spy(new ServicioVendedor(repositorioProducto, repositorioGarantia));
		when(servicioVendedor.calcularFechaFinGarantia(PORCETAJE_DESCUENTO_GARANTIA_MAXIMO,FECHA_INICIO_GARANTIA)).thenReturn(FECHA_FINAL_GARANTIA);
		when(repositorioProducto.obtenerPorCodigo(CODIGO)).thenReturn(producto);

		//act
		Pair<Double,Date> resultado = servicioVendedor.calcularPrecioGarantiaYFecha(CODIGO,FECHA_INICIO_GARANTIA);

		//assert
		assertTrue(resultado.getKey().compareTo(valorEsperado.getKey())==0);
		assertTrue(resultado.getValue().compareTo(valorEsperado.getValue())==0);
	}
	@Test
	public void calcularPrecioGarantiaYFechaTestDebeRetornarDiezPorcientoDelPrecio(){

		//arrange
		final Date FECHA_INICIO_GARANTIA = new GregorianCalendar(2020, Calendar.MAY, 14).getTime();
		final Date FECHA_FINAL_GARANTIA = new GregorianCalendar(2020, Calendar.AUGUST, 8).getTime();
		Pair<Double,Date> valorEsperado = new Pair<Double, Date>(40000.0,FECHA_FINAL_GARANTIA) ;
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.conCodigo(CODIGO).conPrecio(400000).build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = Mockito.spy(new ServicioVendedor(repositorioProducto, repositorioGarantia));
		when(servicioVendedor.calcularFechaFinGarantia(PORCETAJE_DESCUENTO_GARANTIA_MINIMO,FECHA_INICIO_GARANTIA)).thenReturn(FECHA_FINAL_GARANTIA);
		when(repositorioProducto.obtenerPorCodigo(CODIGO)).thenReturn(producto);

		//act
		Pair<Double,Date> resultado = servicioVendedor.calcularPrecioGarantiaYFecha(CODIGO,FECHA_INICIO_GARANTIA);

		//assert
		assertTrue(resultado.getKey().compareTo(valorEsperado.getKey())==0);
		assertTrue(resultado.getValue().compareTo(valorEsperado.getValue())==0);
	}

	@Test
	public void ingresarGarantiaTestGuardarEnRepositorio(){

		//arrange
		String nombreProducto = "TarjetaGrafica";
		Double precioFinalProducto = 250000.0;
		final Date FECHA_FINAL_GARANTIA = new GregorianCalendar(2020, Calendar.JUNE, 12).getTime();

		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.build();
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);
		when(repositorioProducto.obtenerPorCodigo(CODIGO)).thenReturn(producto);
		Mockito.doNothing().when(repositorioGarantia).agregar(Mockito.any(GarantiaExtendida.class));

		//act
		servicioVendedor.ingresarGarantia(CODIGO,nombreProducto,precioFinalProducto,FECHA_FINAL_GARANTIA);

		//assert
		Mockito.verify(repositorioProducto,Mockito.times(1)).obtenerPorCodigo(CODIGO);
		Mockito.verify(repositorioGarantia,Mockito.times(1)).agregar(Mockito.any(GarantiaExtendida.class));
	}
	@Test
	public void retornarFechaDiasHabilesTestOmitirDiasLunes(){

		//arrange
		final Date FECHA_INICIO_GARANTIA = new GregorianCalendar(2020, Calendar.SEPTEMBER, 3).getTime();
		final Date FECHA_FINAL_GARANTIA = new GregorianCalendar(2020, Calendar.SEPTEMBER, 11).getTime();
		final int MAXIMO_DIAS_SIN_LUNES = 8;

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//act
		final Date FECHA_FINALIZACION = servicioVendedor.retornarFechaDiasHabiles(MAXIMO_DIAS_SIN_LUNES,FECHA_INICIO_GARANTIA);

		//assert
		assertTrue(FECHA_FINALIZACION.compareTo(FECHA_FINAL_GARANTIA) == 0);
	}

	@Test
	public void retornarFechaDiasHabilesTestSiTerminaDomingoAplazarHastaDiaHabil(){

		//arrange
		final Date FECHA_INICIO_GARANTIA = new GregorianCalendar(2020, Calendar.SEPTEMBER, 3).getTime();
		final Date FECHA_FINAL_GARANTIA = new GregorianCalendar(2020, Calendar.SEPTEMBER, 8).getTime();
		final int MAXIMO_DIAS_SIN_LUNES = 4;

		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		ServicioVendedor servicioVendedor = new ServicioVendedor(repositorioProducto, repositorioGarantia);

		//act
		final Date FECHA_FINALIZACION = servicioVendedor.retornarFechaDiasHabiles(MAXIMO_DIAS_SIN_LUNES,FECHA_INICIO_GARANTIA);

		//assert
		assertTrue(FECHA_FINALIZACION.compareTo(FECHA_FINAL_GARANTIA) == 0);
	}
}
