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
package de.metanome.backend.results_db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.annotations.GwtCompatible;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOConnection;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Represents a MinIO connection in the database.
 *
 */
@Entity
@GwtCompatible
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MinIOConnection.class, name = "minIOConnection")
})
public class MinIOConnection extends Input implements Serializable {

  private static final long serialVersionUID = 4924078640889259328L;

  protected String url;
  protected String key;
  protected String secretKey;
  protected String comment;

  // Exists for Serialization
  public MinIOConnection() {
  }

  public MinIOConnection(String name) {
    super(name);
  }

  public MinIOConnection(String url, String key, String secretKey) {
    super(ConfigurationSettingMinIOConnection.getIdentifier(url, key));
    this.url = url;
    this.key = key;
    this.secretKey = secretKey;
  }

  @Override
  public MinIOConnection setId(long id) {
    super.setId(id);

    return this;
  }

  public String getUrl() {
    return url;
  }

  public MinIOConnection setUrl(String url) {
    this.url = url;

    return this;
  }

  public String getKey() {
    return key;
  }

  public MinIOConnection setKey(String key) {
    this.key = key;

    return this;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public MinIOConnection setSecretKey(String secretKey) {
    this.secretKey = secretKey;

    return this;
  }


  public String getComment() {
    return comment;
  }

  public MinIOConnection setComment(String comment) {
    this.comment = comment;

    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MinIOConnection)) {
      return false;
    }

    MinIOConnection that = (MinIOConnection) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31)
            .append(super.hashCode())
            .append(url)
            .append(key)
            .append(secretKey)
            .append(comment)
            .toHashCode();
  }

  @Override
  @Transient
  @JsonIgnore
  public String getIdentifier() {
    return ConfigurationSettingMinIOConnection.getIdentifier(this.url, this.key);
  }

}
