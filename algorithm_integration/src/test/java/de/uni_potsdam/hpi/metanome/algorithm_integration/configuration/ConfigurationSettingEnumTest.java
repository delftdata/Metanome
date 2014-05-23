/*
 * Copyright 2014 by the Metanome project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uni_potsdam.hpi.metanome.algorithm_integration.configuration;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSettingEnum}
 */
public class ConfigurationSettingEnumTest {

	/**
	 * Test method for {@link de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSettingEnum#ConfigurationSettingEnum(java.util.ArrayList)}
	 */
	@Test
	public void testConstructor() {
		// Setup
		// Expected values
		ArrayList<String> expectedValues = new ArrayList<>();
		expectedValues.add("first");
		expectedValues.add("second");
		expectedValues.add("third");
		String expectedSelectedValue = "second";

		// Execute functionality
		ConfigurationSettingEnum setting1 = new ConfigurationSettingEnum(expectedValues);
		ConfigurationSettingEnum setting2 = new ConfigurationSettingEnum(expectedValues, expectedSelectedValue);

		// Check result
		assertEquals(expectedValues, setting1.value);
		assertEquals(expectedValues, setting2.value);
		assertEquals(expectedSelectedValue, setting2.selectedValue);
	}
}