package me.cbitler.raidbot.utility;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static me.cbitler.raidbot.utility.Variables.RaidBotProperty.TEST;

class VariablesTest {

	private static final String testString = "test successfull";

	@Test
	void getInstance() {
		Assert.assertNotNull(Variables.getINSTANCE());
	}

	@Test
	void getStringProperty() {
		Assertions.assertEquals(Variables.getINSTANCE().getStringProperty(TEST.toString()), testString);
	}
}