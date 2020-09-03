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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorProductoTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getProductoPorId() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/productos/{id}", "PROD_001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value("PROD_001"));
    }

    @Test
    public void crearProducto() throws Exception
    {
        ComandoProducto comandoProducto= new ProductoTestDataBuilder().buildComando();
        mvc.perform( MockMvcRequestBuilders
                .post("/productos")
                .content(objectMapper.writeValueAsString(comandoProducto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
