/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.services;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;

public class UserInfoUpdater implements Runnable {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final static Logger LOGGER = LoggerFactory.getLogger(UserInfoUpdater.class);
  
  private static int THREAD_SLEEP = 5000;
  
  //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private BatchJob batchJob;

  private UserInfoService userInfoService;
  
  private List<UserInfo> retryQueue = new LinkedList<UserInfo>();

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public UserInfoUpdater(BatchJob batchJob, UserInfoService userInfoService) {
    this.batchJob = batchJob;
    this.userInfoService = userInfoService;
  }
  
  /**
   * For unit testing, allow for the adjustment of thread sleeping
   * @param batchJob
   * @param userInfoService
   * @param threadSleepDuration
   */
  protected UserInfoUpdater(BatchJob batchJob, UserInfoService userInfoService, int threadSleepDuration) {
    this.batchJob = batchJob;
    this.userInfoService = userInfoService;
    THREAD_SLEEP = threadSleepDuration;
  } 

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  @Override
  public void run() {

    LOGGER.info("Starting Batch Update {}", batchJob.getId());
    
    for(String userInfoId : batchJob.getUserInfoIds()) {
      UserInfo userInfo = userInfoService.getUserInfo(userInfoId);

      if (batchJob.getBatchJobType() == BatchJobType.INDUSTRY) {
        userInfo.setIndustry(batchJob.getToValue());
      } else if (batchJob.getBatchJobType() == BatchJobType.JOB_TITLE) {
        userInfo.setJobTitle(batchJob.getToValue());
      }
     
      updateUserInfo(userInfo);

      try {
        Thread.sleep(THREAD_SLEEP);
      } catch (InterruptedException e) {
        // TODO: handle exception
      }
    }
    
    boolean retryQueueEmpty = retryQueue.isEmpty();
    while (!retryQueueEmpty) {
      updateUserInfo(retryQueue.remove(0));
      try {
        Thread.sleep(THREAD_SLEEP);
      } catch (InterruptedException e) {
        // TODO: handle exception
      }
      retryQueueEmpty = retryQueue.isEmpty();
    }
    
  }

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  private void updateUserInfo(UserInfo userInfo) {
    try {
      CreateUserInfoDto createUserInfoDto = userInfo.createUserInfoDto();
      userInfoService.update(userInfo.getUserId(), createUserInfoDto);
    } catch (Exception e) {
      LOGGER.error("Could not user info {}", userInfo.getUserId(), e.getMessage());
      LOGGER.error("Placing it back on the RERTY QUEUE");
      retryQueue.add(userInfo);
    }
  }

  //---------------------------- Property Methods -----------------------------

}
