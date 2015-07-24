/*
 * © Copyright 2015 -  SourceClear Inc
 */


package com.charles.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.charles.data.mongo")
public class MongoConfig extends AbstractMongoConfiguration{

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private String mongoUsername = "charles";

  private String mongoPassword = "mongodb";

  private String mongoDbName = "userinfo";

  private String mongoHost = "dbh73.mongolab.com";

  private int mongoPort = 27737;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  @Override
  protected UserCredentials getUserCredentials() {
    return new UserCredentials(mongoUsername, mongoPassword);
  }

  @Override
  protected String getDatabaseName() {
    return mongoDbName;
  }

  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient(mongoHost, mongoPort);
  }

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
}

