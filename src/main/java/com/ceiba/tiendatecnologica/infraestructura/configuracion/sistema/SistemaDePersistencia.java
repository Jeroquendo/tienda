package com.ceiba.tiendatecnologica.infraestructura.configuracion.sistema;

import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioProducto;
import com.ceiba.tiendatecnologica.infraestructura.configuracion.conexion.ConexionJPA;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio.RepositorioGarantiaPersistente;
import com.ceiba.tiendatecnologica.infraestructura.persistencia.repositorio.RepositorioProductoPersistente;

import javax.persistence.EntityManager;

public class SistemaDePersistencia {

	private EntityManager entityManager;

	public SistemaDePersistencia() {
		this.entityManager = new ConexionJPA().createEntityManager();
	}

	public RepositorioProducto obtenerRepositorioProductos() {
		return new RepositorioProductoPersistente(entityManager);
	}
	
	public RepositorioGarantiaExtendida obtenerRepositorioGarantia() {
		return new RepositorioGarantiaPersistente(entityManager, this.obtenerRepositorioProductos());
	}

	public void iniciar() {
		entityManager.getTransaction().begin();
	}

	public void terminar() {
		entityManager.getTransaction().commit();
	}
}
