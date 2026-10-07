package com.energi.backend;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.energi.backend.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.verify;

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
    @Test
    void shouldCreateComponent() throws Exception {
        Component savedComponent = new Component("Battery 1", Component.Status.ACTIVE, "Battery");

        when(componentService.newComponent(any(Component.class))).thenReturn(savedComponent);

        mockMvc.perform(post("/api/components").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Battery 1",
                          "status": "ACTIVE",
                          "type": "Battery"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Battery 1"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.type").value("Battery"));
    }

    @Test
    void shouldReturn400WhenCreatingInvalidComponent() throws Exception {
        mockMvc.perform(post("/api/components").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "",
                          "status": "ACTIVE",
                          "type": "Battery"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
    @Test
    void shouldDeleteComponent() throws Exception {
        mockMvc.perform(delete("/api/components/1")).andExpect(status().isNoContent());
        verify(componentService).deleteComponent(1L);
    }
    @Test
    void shouldUpdateComponent() throws Exception {
        Component updatedComponent = new Component("Updated Battery", Component.Status.MAINTENANCE, "Battery");

        when(componentService.updateComponent(eq(1L), any(Component.class))).thenReturn(updatedComponent);

        mockMvc.perform(put("/api/components/1").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Updated Battery",
                          "status": "MAINTENANCE",
                          "type": "Battery"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Battery"))
                .andExpect(jsonPath("$.status").value("MAINTENANCE"));
    }

}

