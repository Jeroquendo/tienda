package com.ceiba.tiendatecnologica.aplicacion.manejadores.garantia;

import com.ceiba.tiendatecnologica.dominio.GarantiaExtendida;
import com.ceiba.tiendatecnologica.dominio.servicio.garantia.ServicioObtenerGarantia;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorObtenerGarantia {

    private final ServicioObtenerGarantia servicioObtenerGarantia;

    public ManejadorObtenerGarantia(ServicioObtenerGarantia servicioObtenerGarantia) {
        this.servicioObtenerGarantia = servicioObtenerGarantia;
    }

    @Transactional
    public GarantiaExtendida ejecutar(String codigo) {
        return this.servicioObtenerGarantia.ejecutar(codigo) ;
    }
}
