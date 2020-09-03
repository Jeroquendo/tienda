package com.ceiba.tiendatecnologica.aplicacion.fabrica;

import com.ceiba.tiendatecnologica.aplicacion.comando.ComandoProducto;
import com.ceiba.tiendatecnologica.dominio.Producto;
import org.springframework.stereotype.Component;

@Component
public class FabricaProducto {
    public Producto crearProducto(ComandoProducto comandoProducto){
        return new Producto(comandoProducto.getId(),comandoProducto.getCodigo(),comandoProducto.getNombre(),comandoProducto.getPrecio());
    }
}
