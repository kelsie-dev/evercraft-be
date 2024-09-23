package com.accenture.boot.camp.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
	private CharacterSheet newCharacter;
	private CharacterSheet targetCharacter;
	private TargetAction attack;

	@BeforeEach
	public void beforeEach() {
		newCharacter = new CharacterSheet("Edgar", "Good");
		targetCharacter = new CharacterSheet("Kefka", "Evil");

	}

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

	@Test
	void a_character_has_a_default_armor_class() {
		//given a new character
		//when the character is instantiated
		int expectedArmorClass = 10;
		int expectedHitPoints = 5;
		//then the character is given a default armor class of 10 and default hit points of 5
		Assertions.assertEquals(expectedArmorClass, newCharacter.getArmorClass());
		Assertions.assertEquals(expectedHitPoints, newCharacter.getHitPoints());
	}

	@Test
	void a_d20_roll_returns_a_value_between_1_and_20() {
		//given a character enters combat
		//when the character rolls a d20 for an attack
		//then the die roll will return a number between 1 and 20
		int expectedValidValue = 4;
		int expectedInvalidValue = 21;

		Assertions.assertEquals(expectedValidValue, newCharacter.roll(expectedValidValue));
		Assertions.assertEquals(-1, newCharacter.roll(expectedInvalidValue));
	}

	@Test
	void roll_meets_or_beats_armor_class_of_opponent_to_hit() {
		//given a character is in combat
		//when the roll is made for the attack
		//and the roll is greater than or equal to the opponent's hit points
		//then the action is successful

		int dieRoll = newCharacter.roll(10);
		attack = new TargetAction(newCharacter, targetCharacter);
		Assertions.assertTrue(attack.isSuccessful(dieRoll));
	}

	@Test
	void character_does_not_meet_or_beat_target_armor_class() {
		//given a character is performing an attack
		//when the die is rolled to attack a target
		//and the die number returned is not greater than or equal to the target armor class
		//then the attack is not successful

		int dieRoll = newCharacter.roll(5);
		attack = new TargetAction(newCharacter, targetCharacter);
		Assertions.assertFalse(attack.isSuccessful(dieRoll));
	}

	@Test
	void target_character_takes_damage_and_loses_hit_point() {
		//given a character's attack is on the target character
		//when the target character is hit successfully
		//then the target character loses a hit point

		int dieRoll = newCharacter.roll(10);
		attack = new TargetAction(newCharacter, targetCharacter);

		Assertions.assertEquals(targetCharacter.getHitPoints()-1, attack.dealDamage(dieRoll));
	}

	@Test
	void die_roll_natural_20_deals_double_damage() {
		//given a character's attack roll is a natural 20
		int roll = newCharacter.roll(20);
		int targetHpBeforeAttack = targetCharacter.getHitPoints();
		attack = new TargetAction(newCharacter, targetCharacter);
		//when the character is hit successfully
		int preRollDamage = attack.getDamage();
		attack.dealDamage(roll);
		int criticalDamage = attack.getDamage();
		int targetHpAfterAttack = targetHpBeforeAttack - attack.getDamage();
		//then the target character loses double the normal hit points

		Assertions.assertEquals(targetHpAfterAttack, targetHpBeforeAttack - criticalDamage);
		Assertions.assertEquals(preRollDamage * 2, criticalDamage);
	}

	@Test
	void when_hit_points_reach_zero_character_is_dead() {
		//given a character's HP is zero
		int currentHealth = targetCharacter.getHitPoints();
		targetCharacter.takeDamage(currentHealth);
		//then player dies
		Assertions.assertFalse(targetCharacter.getAlive());
	}
}
