package com.accenture.boot.camp.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class EvercraftApplicationTests {

	@Autowired
	private MockMvc mvc;

	public CharacterSheet newCharacter = new CharacterSheet("Edgar", "Good");

	@Test
	void person_endpoint_will_default_name_to_person_if_a_name_is_not_provided_as_a_request_parameter() throws Exception {
		//given the user does not provide a name in the request parameters
		//when we call the endpoint
		MvcResult result = mvc.perform(get("/person")).andReturn();

		//then the endpoint greets us as "Person"
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(result.getResponse().getContentAsString(), "Hello, Person!");
	}

	@Test
	void person_endpoint_will_greet_you_by_your_first_name_if_provided() throws Exception {
		//given the user provides their name in the request parameters
		String userName = "Kelsie";
		//when we call the endpoint
		MvcResult result = mvc.perform(get("/person?name=" + userName)).andReturn();
		//then the endpoint greets the user by name
		Assertions.assertEquals(200, result.getResponse().getStatus());
		Assertions.assertEquals(result.getResponse().getContentAsString(), "Hello, " + userName + "!");
	}

	@Test
	void creating_a_new_character_with_a_name_sets_the_name_attribute() {
		//given the user creates a new character
		//when the character is initialized with a name
		String expectedName = "Edgar";
		//then the instance of the character has the given name
		Assertions.assertEquals(expectedName, newCharacter.getName());
	}

	@Test
	void a_character_must_have_an_alignment_of_good_evil_or_neutral() {
		//give the user has created a new character,
		//when an alignment is selected,
		String expectedAlignment = "Good";
		//then the alignment attribute will be either good, evil, or neutral
		Assertions.assertEquals(expectedAlignment, newCharacter.getAlignment());
	}
}
