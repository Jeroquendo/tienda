package com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio;

import com.ceiba.tiendatecnologica.dominio.Producto;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.builder.ProductoBuilder;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.entidad.ProductoEntity;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio.jpa.RepositorioProductoJPA;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class RepositorioProductoPersistente implements RepositorioProducto, RepositorioProductoJPA {

	private static final String CODIGO = "codigo";
	private static final String PRODUCTO_FIND_BY_CODIGO = "Producto.findByCodigo";
	
	private EntityManager entityManager;

	public RepositorioProductoPersistente(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public Producto obtenerPorCodigo(String codigo) {
		
		ProductoEntity productoEntity = obtenerProductoEntityPorCodigo(codigo);
		return ProductoBuilder.convertirADominio(productoEntity);
	}
	
	@Override
	public ProductoEntity obtenerProductoEntityPorCodigo(String codigo) {
		
		Query query = entityManager.createNamedQuery(PRODUCTO_FIND_BY_CODIGO);
		query.setParameter(CODIGO, codigo);

		return (ProductoEntity) query.getSingleResult();
	}

	@Override
	public void agregar(Producto producto) {
		entityManager.persist(ProductoBuilder.convertirAEntity(producto));
	}	

	

}
