package com.charles.data;

public enum BatchJobType {

  INDUSTRY("INDUSTRY"),
  JOB_TITIE("JOB_TITLE");
  
  private String value;
  
  private BatchJobType(String value){
    this.value = value;
  }
}
