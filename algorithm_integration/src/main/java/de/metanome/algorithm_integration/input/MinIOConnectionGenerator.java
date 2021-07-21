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
package de.metanome.algorithm_integration.input;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import io.minio.MinioClient;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Generates new copies of {@link RelationalInput}s or {@link ResultSet}s.
 *
 * @author Jakob Zwiener
 */
public interface MinIOConnectionGenerator extends AutoCloseable {


  RelationalInput generateRelationalInputFromMinIOObject(String bucketName, String objectName)
    throws InputGenerationException, AlgorithmConfigurationException;

  ArrayList<String> getMinIOObjectNames(String bucketName);

  MinioClient getClient();

}
