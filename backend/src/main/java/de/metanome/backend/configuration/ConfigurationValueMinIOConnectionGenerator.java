/**
 * Copyright 2014-2016 by Metanome Project
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
package de.metanome.backend.configuration;

import de.metanome.algorithm_integration.Algorithm;
import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.algorithm_types.DatabaseConnectionParameterAlgorithm;
import de.metanome.algorithm_integration.algorithm_types.MinIOConnectionParameterAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementDatabaseConnection;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementMinIOConnection;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingDatabaseConnection;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOConnection;
import de.metanome.algorithm_integration.input.DatabaseConnectionGenerator;
import de.metanome.algorithm_integration.input.MinIOConnectionGenerator;
import de.metanome.backend.input.database.DefaultDatabaseConnectionGenerator;
import de.metanome.backend.input.minio.DefaultMinIOConnectionGenerator;
import de.metanome.backend.results_db.AlgorithmType;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * Represents database connection configuration values for {@link Algorithm}s.
 *
 * @author Jakob Zwiener
 */
public class ConfigurationValueMinIOConnectionGenerator
  extends
  ConfigurationValue<MinIOConnectionGenerator, ConfigurationRequirementMinIOConnection> {

  protected ConfigurationValueMinIOConnectionGenerator() {
  }

  public ConfigurationValueMinIOConnectionGenerator(String identifier,
                                                    MinIOConnectionGenerator... values) {
    super(identifier, values);
  }

  public ConfigurationValueMinIOConnectionGenerator(
    ConfigurationRequirementMinIOConnection requirement)
    throws AlgorithmConfigurationException, FileNotFoundException {
    super(requirement);
  }

  @Override
  protected MinIOConnectionGenerator[] convertToValues(
    ConfigurationRequirementMinIOConnection requirement)
    throws AlgorithmConfigurationException {
    ConfigurationSettingMinIOConnection[] settings = requirement.getSettings();
    MinIOConnectionGenerator[] newValues = new MinIOConnectionGenerator[settings.length];

    for (int i = 0; i < settings.length; i++) {
      newValues[i] = new DefaultMinIOConnectionGenerator(settings[i]);
    }

    return newValues;
  }

  @Override
  public void triggerSetValue(Algorithm algorithm, Set<Class<?>> algorithmInterfaces)
    throws AlgorithmConfigurationException {
    if (!algorithmInterfaces.contains(AlgorithmType.DB_CONNECTION.getAlgorithmClass())) {
      throw new AlgorithmConfigurationException(
        "Algorithm does not accept database connection input configuration values.");
    }

    MinIOConnectionParameterAlgorithm
      minIOConnectionParameterAlgorithm = (MinIOConnectionParameterAlgorithm) algorithm;
    minIOConnectionParameterAlgorithm.setMinIOConnectionGeneratorConfigurationValue(
      this.identifier, this.values);
  }
}
