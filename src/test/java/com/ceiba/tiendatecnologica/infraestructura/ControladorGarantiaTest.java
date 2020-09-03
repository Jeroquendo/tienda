package com.ceiba.tiendatecnologica.infraestructura;

import com.ceiba.tiendatecnologica.aplicacion.comando.ComandoProducto;
import com.ceiba.tiendatecnologica.testdatabuilder.ProductoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorGarantiaTest {
    public static final String ESTE_PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantía extendida";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void generarGarantiaProducto() throws Exception
    {
        ComandoProducto comandoProducto= new ProductoTestDataBuilder().buildComando();
        mvc.perform( MockMvcRequestBuilders
                .post("/garantia/{idProducto}/{nombreCliente}","F01TSA0150","CLIENTE")
                .content(objectMapper.writeValueAsString(comandoProducto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
