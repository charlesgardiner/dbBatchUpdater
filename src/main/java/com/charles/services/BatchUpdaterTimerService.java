/*
 * © Copyright 2015 -  Charles Gardiner
 */
package com.charles.services;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.charles.data.BatchJob;
import com.charles.data.mongo.BatchJobRepository;

@Component
public class BatchUpdaterTimerService {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final static Logger LOGGER = LoggerFactory.getLogger(BatchUpdaterTimerService.class);
  
  private final static int THREAD_POOL_SIZE = 4;
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private BatchJobRepository batchJobRepository;
  
  @Autowired
  private UserInfoService userInfoService;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

 // @Scheduled(cron = "")
  @Scheduled(fixedRate = 30000)
  @Async
  public void startDatabaseUpdate(){
    
    LOGGER.info("Starting Database Update Process");
    
    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    
    List<BatchJob> batchJobList = batchJobRepository.findAll();
    for (BatchJob batchJob : batchJobList) {
      UserInfoUpdater userInfoUpdater = new UserInfoUpdater(batchJob, userInfoService);
      executorService.execute(userInfoUpdater);
    }
    executorService.shutdown();
    
    while (!executorService.isTerminated()) {
    }
    LOGGER.info("All Batch Executors Have Finished");
    
    // remove all the existing batch jobs
    batchJobRepository.delete(batchJobList);
    
  }
  
  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

  
}
