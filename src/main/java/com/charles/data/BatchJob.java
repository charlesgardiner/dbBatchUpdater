/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BatchJob {

  // /////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Id
  public String id;

  public String fromValue;

  private String toValue;

  private List<String> userInfoIds = new ArrayList<String>();
  
  private BatchJobType batchJobType;

  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public BatchJob() {
  }
  
  
  public BatchJob(String id, String fromValue, String toValue,
      List<String> userInfoIds, BatchJobType batchJobType) {
    super();
    this.id = id;
    this.fromValue = fromValue;
    this.toValue = toValue;
    this.userInfoIds = userInfoIds;
    this.batchJobType = batchJobType;
  }


  // //////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ------------------------ Implements:

  // ------------------------ Overrides:

  // ---------------------------- Abstract Methods -----------------------------

  // ---------------------------- Utility Methods ------------------------------

  // ---------------------------- Property Methods -----------------------------

  public String getFromValue() {
    return fromValue;
  }

  public void setFromValue(String fromValue) {
    this.fromValue = fromValue;
  }

  public String getToValue() {
    return toValue;
  }

  public void setToValue(String toValue) {
    this.toValue = toValue;
  }

  public List<String> getUserInfoIds() {
    return userInfoIds;
  }

  public void setUserInfoIds(List<String> userInfoIds) {
    this.userInfoIds = userInfoIds;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BatchJobType getBatchJobType() {
    return batchJobType;
  }

  public void setBatchJobType(BatchJobType batchJobType) {
    this.batchJobType = batchJobType;
  }


  
}
