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

import de.metanome.backend.constants.Constants;
import de.metanome.backend.results_db.HibernateUtil;
import de.metanome.backend.results_db.MinIOConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Responsible for the database communication for DatabaseConnection and for handling all restful
 * calls of DatabaseConnections.
 *
 * @author Moritz Finke
 */

@Path("minio-connections")
public class MinIOConnectionResource implements Resource<MinIOConnection> {

  /**
   * @return all DatabaseConnections in the database
   */
  @GET
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @SuppressWarnings(Constants.SUPPRESS_WARNINGS_UNCHECKED)
  @Override
  public List<MinIOConnection> getAll() {
    try {
      return (List<MinIOConnection>) HibernateUtil.queryCriteria(MinIOConnection.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * retrieves a DatabaseConnection
   *
   * @param id the id of the DatabaseConnection
   * @return the retrieved DatabaseConnection
   */
  @GET
  @Path("/get/{id}")
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Override
  public MinIOConnection get(@PathParam("id") long id) {
    try {
      return (MinIOConnection) HibernateUtil.retrieve(MinIOConnection.class, id);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * Passes parameter to store function
   *
   * @param connection the database to be stored
   */
  @POST
  @Path(Constants.STORE_RESOURCE_PATH)
  @Consumes(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  public MinIOConnection executeDatabaseStore(MinIOConnection connection) {
    try {
      return store(connection);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }


  /**
   * Adds a DatabaseConnection to the database.
   *
   * @param connection the database to be stored
   * @return the stored DatabaseConnection
   */

  public MinIOConnection store(MinIOConnection connection) {
    try {
      HibernateUtil.store(connection);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * Deletes the DatabaseConnection, which has the given id, from the database.
   *
   * @param id the id of the DatabaseConnection, which should be deleted
   */
  @DELETE
  @Path("/delete/{id}")
  @Override
  public void delete(@PathParam("id") long id) {
    try {
      MinIOConnection
              connection =
        (MinIOConnection) HibernateUtil.retrieve(MinIOConnection.class, id);
      HibernateUtil.delete(connection);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

  /**
   * Updates a database connection in the database.
   *
   * @param connection the database connection
   * @return the updated database connection
   */
  @POST
  @Path("/update")
  @Consumes(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Produces(Constants.APPLICATION_JSON_RESOURCE_PATH)
  @Override
  public MinIOConnection update(MinIOConnection connection) {
    try {
      HibernateUtil.update(connection);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebException(e, Response.Status.BAD_REQUEST);
    }
  }

}
