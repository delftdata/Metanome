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
import de.metanome.algorithm_integration.algorithm_types.MinIOInputParameterAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementMinIOInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOInput;
import de.metanome.algorithm_integration.input.MinIOInputGenerator;
import de.metanome.backend.input.minio.DefaultMinIOInputGenerator;
import de.metanome.backend.results_db.AlgorithmType;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * Represents {@link MinIOInputGenerator} configuration
 * values for {@link Algorithm}s.
 *
 * @author Jakob Zwiener
 */
public class ConfigurationValueMinIOInputGenerator
  extends ConfigurationValue<MinIOInputGenerator, ConfigurationRequirementMinIOInput> {

  protected ConfigurationValueMinIOInputGenerator() {
  }

  public ConfigurationValueMinIOInputGenerator(String identifier,
                                               MinIOInputGenerator... values) {
    super(identifier, values);
  }

  public ConfigurationValueMinIOInputGenerator(ConfigurationRequirementMinIOInput requirement)
    throws AlgorithmConfigurationException, FileNotFoundException {
    super(requirement);
  }

  @Override
  protected MinIOInputGenerator[] convertToValues(ConfigurationRequirementMinIOInput requirement)
    throws AlgorithmConfigurationException {
    ConfigurationSettingMinIOInput[] settings = requirement.getSettings();
    MinIOInputGenerator[] configValues = new MinIOInputGenerator[settings.length];
    for (int i = 0; i < settings.length; i++) {
      configValues[i] = new DefaultMinIOInputGenerator(settings[i]);
    }
    return configValues;
  }

  @Override
  public void triggerSetValue(Algorithm algorithm, Set<Class<?>> algorithmInterfaces)
    throws AlgorithmConfigurationException {
    if (!algorithmInterfaces.contains(AlgorithmType.FILE_INPUT.getAlgorithmClass())) {
      throw new AlgorithmConfigurationException(
        "Algorithm does not accept MinIO input configuration values.");
    }

    MinIOInputParameterAlgorithm MinIOInputParameterAlgorithm =
      (MinIOInputParameterAlgorithm) algorithm;
    MinIOInputParameterAlgorithm.setMinIOInputConfigurationValue(identifier, values);
  }

}
