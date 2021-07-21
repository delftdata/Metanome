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
package de.metanome.backend.resources;

import de.metanome.algorithm_integration.configuration.ConfigurationSettingMinIOConnection;
import de.metanome.backend.constants.Constants;
import de.metanome.backend.input.minio.DefaultMinIOConnectionGenerator;
import de.metanome.backend.results_db.HibernateUtil;
import de.metanome.backend.results_db.MinIOConnection;
import de.metanome.backend.results_db.MinIOInput;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("minio-inputs")
public class MinIOInputResource implements Resource<MinIOInput> {

  @POST
  @Path("/bucket")
  @Consumes(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  public void bucket(MinIOInput input) {
    try {

        MinIOConnection connection = (MinIOConnection) HibernateUtil.retrieve(MinIOConnection.class, input.getMinIOConnection().getId());
      ArrayList<String> objects = new DefaultMinIOConnectionGenerator(new ConfigurationSettingMinIOConnection(
              connection.getUrl(),
              connection.getKey(),
              connection.getSecretKey()
      )).getMinIOObjectNames(input.getBucketName());

      objects.forEach(o -> {
        if (Arrays.stream(Constants.ACCEPTED_FILE_ENDINGS_ARRAY).anyMatch(o::endsWith)) {
          store(new MinIOInput(o, input.getBucketName(), input.getMinIOConnection()));
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }





  /**
   * Stores the TableInput in the database.
   *
   * @return the TableInput
   */
  @POST
  @Path(Constants.STORE_RESOURCE_PATH)
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Consumes(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Override
  public MinIOInput store(MinIOInput input) {
    try {
      HibernateUtil.store(input);
      return input;
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
//    try {
//      HibernateUtil.store(input);
//      return input;
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new WebException(e, Response.Status.BAD_REQUEST);
//    }
  }

  /**
   * Deletes the TableInput, which has the given id, from the database.
   *
   * @param id the id of the TableInput, which should be deleted
   */
  @DELETE
  @Path("/delete/{id}")
  @Override
  public void delete(@PathParam("id") long id) {
    try {
      MinIOInput input = (MinIOInput) HibernateUtil.retrieve(MinIOInput.class, id);
      HibernateUtil.delete(input);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * retrieves a TableInput from the Database
   *
   * @param id the id of the TableInput
   * @return the retrieved TableInput
   */
  @GET
  @Path("/get/{id}")
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Override
  public MinIOInput get(@PathParam("id") long id) {
    try {
      return (MinIOInput) HibernateUtil.retrieve(MinIOInput.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * @return all TableInputs in the database
   */
  @GET
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @SuppressWarnings(Constants.SUPPRESS_WARNINGS_UNCHECKED)
  @Override
  public List<MinIOInput> getAll() {
    try {
      return (List<MinIOInput>) HibernateUtil.queryCriteria(MinIOInput.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * Updates a table input in the database.
   *
   * @param input the table input
   * @return the updated table input
   */
  @POST
  @Path("/update")
  @Consumes(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Override
  public MinIOInput update(MinIOInput input) {
    try {
      HibernateUtil.update(input);
      return input;
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }
}


