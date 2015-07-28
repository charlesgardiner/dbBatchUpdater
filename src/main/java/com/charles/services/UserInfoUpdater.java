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

/**
 * Thread based class to execute a batch job update.
 * 
 * 
 * @author Charles
 *
 */
public class UserInfoUpdater implements Runnable {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final static Logger LOGGER = LoggerFactory.getLogger(UserInfoUpdater.class);
  
  // sleep five seconds
  private static int THREAD_SLEEP = 5000;
  
  //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private BatchJob batchJob;

  private UserInfoService userInfoService;
  
  private List<UserInfo> retryQueue = new LinkedList<UserInfo>();

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Constructor
   * @param batchJob the batch job to execute
   * @param userInfoService constructor injected user info service
   */
  public UserInfoUpdater(BatchJob batchJob, UserInfoService userInfoService) {
    this.batchJob = batchJob;
    this.userInfoService = userInfoService;
  }
  
  /**
   * For unit testing, allow for the adjustment of thread sleeping
   * @param batchJob the batch job
   * @param userInfoService the user info service
   * @param threadSleepDuration the duration of the thread sleep
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
       LOGGER.error("Thread for User Info Updater {} interrupted", batchJob.getId());
      }
    }
    
    boolean retryQueueEmpty = retryQueue.isEmpty();
    while (!retryQueueEmpty) {
      updateUserInfo(retryQueue.remove(0));
      try {
        Thread.sleep(THREAD_SLEEP);
      } catch (InterruptedException e) {
        LOGGER.error("Thread for User Info Updater {} interrupted", batchJob.getId());
      }
      retryQueueEmpty = retryQueue.isEmpty();
    }
    
  }

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  /**
   * Updates the user info
   * @param userInfo the user info object to update
   */
  private void updateUserInfo(UserInfo userInfo) {
    try {
      CreateUserInfoDto createUserInfoDto = userInfo.createUserInfoDto();
      userInfoService.update(userInfo.getUserId(), createUserInfoDto);
    } catch (Exception e) {
      
      // if an exception is thrown during the update.  Place userInfo object on the retry queue.
      
      LOGGER.error("Could not user info {}", userInfo.getUserId(), e.getMessage());
      LOGGER.error("Placing it back on the RERTY QUEUE");
      retryQueue.add(userInfo);
    }
  }

  //---------------------------- Property Methods -----------------------------

}
