@WebMvcTest(MeasurementController.class)
class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementRepository repository;

    @Test
    void shouldRejectInvalidPayload() throws Exception {

        String invalidJson = """
        {
            "temperature": -999
        }
        """;

        mockMvc.perform(
                        post("/api/measurements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson))
                .andExpect(status().isBadRequest());

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