/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.data.dto;

public class CreateBatchJobDto {

  // /////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private String fromValue;
  
  private String toValue;
  
  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public CreateBatchJobDto() {
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
}
