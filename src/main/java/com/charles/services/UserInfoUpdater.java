/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;

public class UserInfoUpdater implements Runnable {

  // /////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final static Logger LOGGER= LoggerFactory.getLogger(UserInfoUpdater.class);
  
  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private BatchJob batchJob;

  private UserInfoService userInfoService;

  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public UserInfoUpdater(BatchJob batchJob, UserInfoService userInfoService) {
    super();
    this.batchJob = batchJob;
    this.userInfoService = userInfoService;
  }

  // //////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ------------------------ Implements:

  // ------------------------ Overrides:

  @Override
  public void run() {

    for(String userInfoId : batchJob.getUserInfoIds()) {
      UserInfo userInfo = userInfoService.getUserInfo(userInfoId);

      if(batchJob.getBatchJobType() == BatchJobType.INDUSTRY) {
        userInfo.setIndustry(batchJob.getToValue());
      } else if(batchJob.getBatchJobType() == BatchJobType.JOB_TITIE) {
        userInfo.setJobTitle(batchJob.getToValue());
      }
      userInfoService.update(userInfoId, createUserInfoDto(userInfo));

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        // TODO: handle exception
      }
    }
  }

  // ---------------------------- Abstract Methods -----------------------------

  // ---------------------------- Utility Methods ------------------------------

  private CreateUserInfoDto createUserInfoDto(UserInfo userInfo) {
    CreateUserInfoDto createUserInfoDto = new CreateUserInfoDto();
    BeanUtils.copyProperties(userInfo, createUserInfoDto);
    return createUserInfoDto;
  }

  // ---------------------------- Property Methods -----------------------------

}
