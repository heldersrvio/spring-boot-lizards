package com.heldersrvio.springbootlizards;

import com.heldersrvio.springbootlizards.control.LizardController;
import com.heldersrvio.springbootlizards.model.Lizard;
import com.heldersrvio.springbootlizards.repository.LizardRepository;
import com.heldersrvio.springbootlizards.security.JWTAuthorizationFilter;
import com.heldersrvio.springbootlizards.security.TokenAuthenticationService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
		controllers = LizardController.class,
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)
		},
		excludeAutoConfiguration = {
				SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class
		}
)
class SpringBootLizardsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LizardRepository lizardRepository;

	@Test
	void allWorks() throws Exception {
		List<Lizard> lizards = new ArrayList<>();
		lizards.add(new Lizard("1", "Eublepharis macularius", "Leopard gecko", ""));
		lizards.add(new Lizard("2", "Heloderma suspectum", "Gila monster", ""));
		when(lizardRepository.findAll()).thenReturn(lizards);

		String response = this.mockMvc.perform(get("/lizards").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JSONArray jsonArray = new JSONArray(response);
		assert(jsonArray.length() == 2);

		JSONObject lizard1 = jsonArray.getJSONObject(0);
		assert(lizard1.getString("id").equals("1"));
		assert(lizard1.getString("species").equals("Eublepharis macularius"));
		assert(lizard1.getString("commonName").equals("Leopard gecko"));
		assert(lizard1.getString("picture").equals(""));

		JSONObject lizard2 = jsonArray.getJSONObject(1);
		assert(lizard2.getString("id").equals("2"));
		assert(lizard2.getString("species").equals("Heloderma suspectum"));
		assert(lizard2.getString("commonName").equals("Gila monster"));
		assert(lizard2.getString("picture").equals(""));
	}

	@Test
	void oneWorks() throws Exception {
		Lizard lizard = new Lizard("1", "Eublepharis macularius", "Leopard gecko", "");
		when(lizardRepository.findById(anyString())).thenReturn(Optional.of(lizard));

		String response = this.mockMvc.perform(get("/lizards/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject(response);
		assert(jsonObject.getString("id").equals("1"));
		assert(jsonObject.getString("species").equals("Eublepharis macularius"));
		assert(jsonObject.getString("commonName").equals("Leopard gecko"));
		assert(jsonObject.getString("picture").equals(""));
	}

	@Test
	void createWorks() throws Exception {
		Lizard newLizard = new Lizard("1", "Eublepharis macularius", "Leopard gecko", "");
		when(lizardRepository.save(any())).thenReturn(newLizard);

		String response = this.mockMvc.perform(post("/lizards").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"species\": \"Eublepharis macularius\", \"commonName\": \"Leopard gecko\", \"picture\": \"\" }"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject(response);
		assert(jsonObject.getString("id").equals("1"));
		assert(jsonObject.getString("species").equals("Eublepharis macularius"));
		assert(jsonObject.getString("commonName").equals("Leopard gecko"));
		assert(jsonObject.getString("picture").equals(""));
	}

	@Test
	void deleteWorks() throws Exception {
		this.mockMvc.perform(delete("/lizards/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
