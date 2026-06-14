package com.iot.controllers;
import com.iot.repositories.IMeasurementRepository;
import com.iot.services.EnvironmentalAnalyzer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeasurementController.class)
class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the repository using the new Spring Boot 3.4+ annotation
    @MockitoBean
    private IMeasurementRepository repository;

    // Mock the analyzer so the controller can be built and loaded correctly
    @MockitoBean
    private EnvironmentalAnalyzer analyzer;

    @Test
    void shouldRejectInvalidPayload() throws Exception {

        // Prepare a JSON payload with a temperature completely out of bounds
        String invalidJson = """
        {
            "temperature": -999,
            "humidity": 50,
            "light": 500,
            "co2": 400,
            "buttonPressed": false
        }
        """;

        // Perform the mock POST request to our controller
        mockMvc.perform(
                        post("/api/measurements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson))
                .andExpect(status().isBadRequest()); // Expect an HTTP 400 Bad Request response

        // Verify that the save() method was NEVER called on the database repository
        verify(repository, never()).save(any());
    }
}
/* MAS BASICO:

class MeasurementControllerTest {

    @Test
    void shouldRejectInvalidTemperature() {

        MeasurementController controller =
                new MeasurementController(null);

        MeasurementDTO dto = new MeasurementDTO();
        dto.setTemperature(-999.0);

        assertFalse(controller.validatePayload(dto));
    }
}
 */