package com.energi.backend;

import com.energi.backend.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComponentController.class)
class ComponentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComponentService componentService;

    @Test
    void shouldReturnComponentById() throws Exception {
        Component component =
                new Component("Batteri", Component.Status.ACTIVE, "Batteri");

        when(componentService.getComponentByID(1L)).thenReturn(component);

        mockMvc.perform(get("/api/components/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Batteri"));
    }
    @Test
    void shouldReturn404WhenComponentDoesntExist() throws Exception{
        when(componentService.getComponentByID(200L)).thenThrow(new ResourceNotFoundException("Component not found"));

        mockMvc.perform(get("/api/components/200")).andExpect(status().isNotFound());
    }
}

/*
* Legge inn test som tester:
* Get alle
* Ny komponent
* Ny ugyldig komponent
* Rediger gylidg komponent
* slett gyldig komponent
* HVis tid ugylid slett og rediger
* */