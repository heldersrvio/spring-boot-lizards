package com.heldersrvio.springbootlizards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heldersrvio.springbootlizards.model.Lizard;
import com.heldersrvio.springbootlizards.repository.LizardRepository;
import com.heldersrvio.springbootlizards.util.GenerateJWTToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootLizardsApplicationIntegrationTests {

    private final String token = GenerateJWTToken.JWTGenerate();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LizardRepository lizardRepository;

    private MockHttpServletRequestBuilder setUpRequest(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token);
    }

    @Test
    void authenticationWorks() throws Exception {
        this.mockMvc.perform(get("/lizards/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(403));
    }

    @Test
    void createFindAndDeleteWork() throws Exception {
        Lizard lizard = new Lizard("1", "Eublepharis macularius", "Leopard gecko", null);

        mockMvc.perform(setUpRequest(get("/lizards/1"))).andExpect(status().isNotFound());
        mockMvc.perform(setUpRequest(post("/lizards")).content(objectMapper.writeValueAsString(lizard))).andExpect(status().isOk());

        String response = mockMvc.perform(setUpRequest(get("/lizards/1"))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Lizard newLizard = objectMapper.readValue(response, Lizard.class);
        assert(newLizard.equals(lizard));

        mockMvc.perform(setUpRequest(delete("/lizards/1"))).andExpect(status().isOk());
    }
}
