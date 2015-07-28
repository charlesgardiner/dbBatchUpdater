/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.services;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;

public class UserInfoUpdater implements Runnable {

  // /////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final static Logger LOGGER = LoggerFactory.getLogger(UserInfoUpdater.class);
  
  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private BatchJob batchJob;

  private UserInfoService userInfoService;
  
  private List<UserInfo> retryQueue = new LinkedList<UserInfo>();

  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public UserInfoUpdater(BatchJob batchJob, UserInfoService userInfoService) {
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
     
      updateUserInfo(userInfo);

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        // TODO: handle exception
      }
    }
    
    boolean retryQueueEmpty = retryQueue.isEmpty();
    while( !retryQueueEmpty ){
      updateUserInfo(retryQueue.remove(0));
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        // TODO: handle exception
      }
      retryQueueEmpty = retryQueue.isEmpty();
    }
    
  }

  // ---------------------------- Abstract Methods -----------------------------

  // ---------------------------- Utility Methods ------------------------------

  private void updateUserInfo(UserInfo userInfo) {
    try {
      userInfoService.update(userInfo.getUserId(), createUserInfoDto(userInfo));
    } catch (Exception e) {
      LOGGER.error("Could not user info {}", userInfo.getUserId(), e.getMessage());
      LOGGER.error("Placing it back on the RERTY QUEUE");
      retryQueue.add(userInfo);
    }
  }
  
  protected CreateUserInfoDto createUserInfoDto(UserInfo userInfo) {
    CreateUserInfoDto createUserInfoDto = new CreateUserInfoDto();
    BeanUtils.copyProperties(userInfo, createUserInfoDto);
    return createUserInfoDto;
  }

  // ---------------------------- Property Methods -----------------------------

}
