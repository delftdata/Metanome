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
package de.metanome.algorithm_integration.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.annotations.GwtIncompatible;
import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.input.RelationalInputGeneratorInitializer;

import javax.xml.bind.annotation.XmlTransient;


/**
 * The setting of a {@link ConfigurationRequirementTableInput}
 *
 */
@JsonTypeName("ConfigurationSettingMinIOInput")
public class ConfigurationSettingMinIOInput extends ConfigurationSettingRelationalInput {

  private static final long serialVersionUID = -6370969600042372224L;

  private String object;
  private String bucket;
  private ConfigurationSettingMinIOConnection minIOConnection;

  // Needed for restful serialization
  public String type = "ConfigurationSettingMinIOInput";

  /**
   * Exists for serialization.
   */
  public ConfigurationSettingMinIOInput() {
  }

  public ConfigurationSettingMinIOInput(String object,
                                        ConfigurationSettingMinIOConnection minIOConnection) {
    this.object = object;
    this.minIOConnection = minIOConnection;
  }

  public String getObject() {
    return this.object;
  }

  public ConfigurationSettingMinIOInput setObject(String object) {
    this.object = object;
    return this;
  }

  public String getBucket() {
    return this.bucket;
  }

  public ConfigurationSettingMinIOInput setBucket(String bucket) {
    this.bucket = bucket;
    return this;
  }


  public ConfigurationSettingMinIOConnection getMinIOConnection() {
    return this.minIOConnection;
  }

  public ConfigurationSettingMinIOInput setMinIOConnection(ConfigurationSettingMinIOConnection minIOConnection) {
    this.minIOConnection = minIOConnection;
    return this;
  }

  @Override
  @XmlTransient
  @JsonIgnore
  public String getValueAsString() {
    return ConfigurationSettingMinIOInput.getIdentifier(this.object, this.bucket, this.minIOConnection.getUrl(), this.minIOConnection.getKey());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @XmlTransient
  @GwtIncompatible("Can only be called from backend.")
  public void generate(RelationalInputGeneratorInitializer initializer)
    throws AlgorithmConfigurationException {
    initializer.initialize(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ConfigurationSettingMinIOInput that = (ConfigurationSettingMinIOInput) o;

    if (!object.equals(that.object)) {
      return false;
    }
    if (!bucket.equals(that.bucket)) {
      return false;
    }
    if (!minIOConnection.equals(that.minIOConnection)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = object.hashCode();
    result = 31 * result + bucket.hashCode();
    result = 31 * result + minIOConnection.hashCode();
    return result;
  }

  @XmlTransient
  @JsonIgnore
  public static String getIdentifier(String object, String bucket, String url, String key) {
    return String.format("%s; %s; %s", object, bucket, ConfigurationSettingMinIOConnection.getIdentifier(url, key));
  }

}
