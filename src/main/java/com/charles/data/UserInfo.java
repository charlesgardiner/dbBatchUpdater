/*
 * © Copyright 2015 -  Charles Gardiner
 */


package com.charles.data;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.charles.data.dto.CreateUserInfoDto;

@Document
public class UserInfo {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Id
  private String userId;

  @Field("name")
  private String name;

  @Field("job_title")
  private String jobTitle;

  @Field("industry")
  private String industry;

  @Field("version")
  private long version;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public UserInfo() {
  }

  
  
  public UserInfo(String userId, String name, String jobTitle, String industry, long version) {
    this.userId = userId;
    this.name = name;
    this.jobTitle = jobTitle;
    this.industry = industry;
    this.version = version;
  }



  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public CreateUserInfoDto createUserInfoDto() {
    CreateUserInfoDto createUserInfoDto = new CreateUserInfoDto();
    BeanUtils.copyProperties(this, createUserInfoDto);
    return createUserInfoDto;
  }
  
  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
}

