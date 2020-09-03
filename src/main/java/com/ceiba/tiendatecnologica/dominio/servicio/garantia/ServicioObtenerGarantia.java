package com.ceiba.tiendatecnologica.dominio.servicio.garantia;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.repositorio.RepositorioGarantiaExtendida;
import org.springframework.stereotype.Component;

@Component
public class ServicioObtenerGarantia {
    private RepositorioGarantiaExtendida repositorioGarantiaExtendida;

    public ServicioObtenerGarantia(RepositorioGarantiaExtendida repositorioGarantiaExtendida) {
        this.repositorioGarantiaExtendida = repositorioGarantiaExtendida;
    }

    public GarantiaExtendida ejecutar(String codigo) {
        return this.repositorioGarantiaExtendida.obtener(codigo);
    }
}
