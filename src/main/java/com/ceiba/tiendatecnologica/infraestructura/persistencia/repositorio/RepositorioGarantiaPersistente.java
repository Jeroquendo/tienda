package com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.builder.ProductoBuilder;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.entidad.GarantiaExtendidaEntity;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.entidad.ProductoEntity;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio.jpa.RepositorioProductoJPA;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioGarantiaPersistente implements RepositorioGarantiaExtendida {

	private static final String CODIGO = "codigo";
	private static final String GARANTIA_EXTENDIDA_FIND_BY_CODIGO = "GarantiaExtendida.findByCodigo";

	private EntityManager entityManager;

	private RepositorioProductoJPA repositorioProductoJPA;

	public RepositorioGarantiaPersistente(EntityManager entityManager, RepositorioProducto repositorioProducto) {
		this.entityManager = entityManager;
		this.repositorioProductoJPA = (RepositorioProductoJPA) repositorioProducto;
	}

	@Override
	public void agregar(GarantiaExtendida garantia) {
		GarantiaExtendidaEntity garantiaEntity = buildGarantiaExtendidaEntity(garantia);
		entityManager.persist(garantiaEntity);
		
	}
	
	@Override
	public Producto obtenerProductoConGarantiaPorCodigo(String codigo) {
		
		GarantiaExtendidaEntity garantiaEntity = obtenerGarantiaEntityPorCodigo(codigo);
      		return ProductoBuilder.convertirADominio(garantiaEntity != null ? garantiaEntity.getProducto() : null);
	}
	
	@SuppressWarnings("rawtypes")
	private GarantiaExtendidaEntity obtenerGarantiaEntityPorCodigo(String codigo) {

		Query query = entityManager.createNamedQuery(GARANTIA_EXTENDIDA_FIND_BY_CODIGO);
		query.setParameter(CODIGO, codigo);

		List resultList = query.getResultList();

		return !resultList.isEmpty() ? (GarantiaExtendidaEntity) resultList.get(0) : null;
	}

	private GarantiaExtendidaEntity buildGarantiaExtendidaEntity(GarantiaExtendida garantia) {

		ProductoEntity productoEntity = repositorioProductoJPA.obtenerProductoEntityPorCodigo(garantia.getProducto().getCodigo());

		GarantiaExtendidaEntity garantiaEntity = new GarantiaExtendidaEntity();
		garantiaEntity.setProducto(productoEntity);
		garantiaEntity.setFechaSolicitudGarantia(garantia.getFechaSolicitudGarantia());

		return garantiaEntity;
	}

	
	@Override
	public GarantiaExtendida obtener(String codigo) {
		
		GarantiaExtendidaEntity garantiaEntity = obtenerGarantiaEntityPorCodigo(codigo);

		return new GarantiaExtendida(ProductoBuilder.convertirADominio(garantiaEntity.getProducto()),
				garantiaEntity.getFechaSolicitudGarantia(),garantiaEntity.getFechaFinGarantia(),garantiaEntity.getPrecio(),
				garantiaEntity.getNombreCliente()
				);
	}

	
	
}
