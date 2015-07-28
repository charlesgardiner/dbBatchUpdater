/*
 * © Copyright 2015 -  Charles Gardiner
 */


package com.charles.services;

import java.util.List;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;
import com.charles.data.mongo.BatchJobRepository;
import com.charles.data.mongo.UserInfoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private UserInfoRepository userInfoRepository;
  
  @Autowired
  private BatchJobRepository batchJobRepository;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  public UserInfo createUserInfo(CreateUserInfoDto userInfoDto) {
    UserInfo userInfo = new UserInfo();
    BeanUtils.copyProperties(userInfoDto, userInfo);
    userInfo =  userInfoRepository.save(userInfo);
    
    LOGGER.info("User info {}, {}, {} saved", userInfo.getName(), userInfo.getIndustry(), userInfo.getJobTitle());
    
    // check if this user needs to be added to any existing batch jobs for Industry
    List<BatchJob> matchingIndustryBatchJobs = batchJobRepository.findByFromValueAndBatchJobType(userInfo.getIndustry(), BatchJobType.INDUSTRY);
    for (BatchJob batchJob : matchingIndustryBatchJobs) {
      batchJob.getUserInfoIds().add(userInfo.getUserId());
    }
    if (!matchingIndustryBatchJobs.isEmpty()) {
      batchJobRepository.save(matchingIndustryBatchJobs);
    }
    
    List<BatchJob> matchingJobTitleBatchJobs = batchJobRepository.findByFromValueAndBatchJobType(userInfo.getJobTitle(), BatchJobType.JOB_TITLE);
    for (BatchJob batchJob : matchingJobTitleBatchJobs) {
      batchJob.getUserInfoIds().add(userInfo.getUserId());
    }
    if (!matchingJobTitleBatchJobs.isEmpty()) {
      batchJobRepository.save(matchingJobTitleBatchJobs);
    }
   
    return userInfo;
  }


  public UserInfo getUserInfo(String userId) {
   return userInfoRepository.findOne(userId);
  }

  public UserInfo update(String userId, CreateUserInfoDto userInfoDto) {
    UserInfo userInfo = userInfoRepository.findOne(userId);
    if (userInfo != null) {
      BeanUtils.copyProperties(userInfoDto, userInfo);
      return userInfoRepository.save(userInfo);
    }
    return createUserInfo(userInfoDto);
  }

  public void delete(String userId) {
    userInfoRepository.delete(userId);
  }

  public Page<UserInfo> getPage(Pageable pageable) {
    return userInfoRepository.findAll(pageable);
  }

  
  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
}

