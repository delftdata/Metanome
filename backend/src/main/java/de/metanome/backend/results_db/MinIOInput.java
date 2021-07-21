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
import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOInput;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Represents a table input in the database.
 *
 * @author Jakob Zwiener
 */
@Entity
@GwtCompatible
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MinIOInput.class, name = "minIOInput")
})
public class MinIOInput extends Input implements Serializable {

  private static final long serialVersionUID = -4091112658105793586L;

  protected String objectName;
  protected String bucketName;
  protected MinIOConnection minIOConnection;
  protected String comment;

  // Exists for Serialization
  public MinIOInput() {
  }

  public MinIOInput(String name) {
    super(name);
  }

  public MinIOInput(String objectName, String bucketName, MinIOConnection minIOConnection) {
    super(ConfigurationSettingMinIOInput.getIdentifier(objectName, bucketName, minIOConnection.getUrl(), minIOConnection.getKey()));

    this.objectName = objectName;
    this.bucketName = bucketName;
    this.minIOConnection = minIOConnection;
  }

  public String getObjectName() {
    return objectName;
  }

  public MinIOInput setObjectName(String objectName) {
    this.objectName = objectName;
    return this;
  }

  public String getBucketName() {
    return bucketName;
  }

  public MinIOInput setBucketName(String bucketName) {
    this.bucketName = bucketName;
    return this;
  }

  @ManyToOne(targetEntity = MinIOConnection.class)
  public MinIOConnection getMinIOConnection() {
    return minIOConnection;
  }

  public MinIOInput setMinIOConnection(MinIOConnection minIOConnection) {
    this.minIOConnection = minIOConnection;
    return this;
  }

  public String getComment() {
    return comment;
  }

  public MinIOInput setComment(String comment) {
    this.comment = comment;

    return this;
  }

  @Override
  public MinIOInput setId(long id) {
    super.setId(id);

    return this;
  }

  @Override
  @Transient
  @JsonIgnore
  public String getIdentifier() {
    return ConfigurationSettingMinIOInput.getIdentifier(this.objectName, this.bucketName, this.minIOConnection.getUrl(), this.minIOConnection.getKey());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31)
            .append(super.hashCode())
            .append(objectName)
            .append(bucketName)
            .append(minIOConnection)
            .append(comment)
            .toHashCode();
  }
}