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

import javax.xml.bind.annotation.XmlTransient;

/**
 * The setting of a {@link ConfigurationRequirementDatabaseConnection}
 *
 * @author Claudia Exeler
 */
@JsonTypeName("ConfigurationSettingMinIOConnection")
public class ConfigurationSettingMinIOConnection extends ConfigurationSettingDataSource {

  private static final long serialVersionUID = -7220041878087964L;

  private String url;
  private String key;
  private String secretKey;

  // Needed for restful serialization
  public String type = "ConfigurationSettingMinIOConnection";

  /**
   * Exists for serialization.
   */
  public ConfigurationSettingMinIOConnection() {
  }

  public ConfigurationSettingMinIOConnection(String url, String key, String secretKey) {
    this.url = url;
    this.key = key;
    this.secretKey = secretKey;
  }

  public String getUrl() {
    return url;
  }

  public ConfigurationSettingMinIOConnection setDbUrl(String url) {
    this.url = url;
    return this;
  }

  public String getKey() {
    return key;
  }

  public ConfigurationSettingMinIOConnection setKey(String key) {
    this.key = key;
    return this;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public ConfigurationSettingMinIOConnection setSecretKey(String secretKey) {
    this.secretKey = secretKey;
    return this;
  }

  @Override
  @XmlTransient
  @JsonIgnore
  public String getValueAsString() {
    return ConfigurationSettingMinIOConnection.getIdentifier(this.url, this.key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ConfigurationSettingMinIOConnection that = (ConfigurationSettingMinIOConnection) o;

    if (!url.equals(that.url)) {
      return false;
    }
    if (!secretKey.equals(that.secretKey)) {
      return false;
    }
    if (!key.equals(that.key)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = url.hashCode();
    result = 31 * result + key.hashCode();
    result = 31 * result + secretKey.hashCode();
    return result;
  }

  @XmlTransient
  @JsonIgnore
  public static String getIdentifier(String url, String key) {
    return String.format("%s; %s", url, key);
  }

}
