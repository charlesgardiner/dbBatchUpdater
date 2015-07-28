/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.data;

public enum BatchJobType {

  INDUSTRY("INDUSTRY"),
  JOB_TITLE("JOB_TITLE");
  
  private String value;
  
  private BatchJobType(String value){
    this.value = value;
  }
}
