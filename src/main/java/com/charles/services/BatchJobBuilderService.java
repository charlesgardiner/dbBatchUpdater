package com.charles.services;

/*
 * © Copyright 2015 -  Charles Gardiner
 */

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.mongo.BatchJobRepository;
import com.charles.data.mongo.UserInfoRepository;

@Service
public class BatchJobBuilderService {

  // /////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private BatchJobRepository batchJobRepository;

  @Autowired
  private UserInfoService userInfoService;

  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ------------------------ Implements:

  @Async
  public void buildBatchJob(BatchJob batchJob) {
    int page = 0;
    Page<UserInfo> userInfoPage = userInfoService.getPage(new PageRequest(page, 3));
    while (userInfoPage.hasContent()) {
      if (batchJob.getBatchJobType() == BatchJobType.JOB_TITIE) {
        buildJobTitleBatchJob(batchJob, userInfoPage.getContent());
      } else {
        buildIndustyBatchJob(batchJob, userInfoPage.getContent());
      }
      page++;
      userInfoPage = userInfoService.getPage(new PageRequest(page, 3));

    }

  }

  // ------------------------ Overrides:

  // ---------------------------- Abstract Methods -----------------------------

  // ---------------------------- Utility Methods ------------------------------

  private void buildJobTitleBatchJob(BatchJob batchJob,
      List<UserInfo> userInfoList) {
    List<String> matchingUserInfoIds = userInfoList
        .stream()
        .filter(userInfo -> userInfo.getJobTitle().equals(batchJob.getFromValue()))
        .map(userInfo -> userInfo.getUserId()).collect(Collectors.toList());
    batchJob.getUserInfoIds().addAll(matchingUserInfoIds);
    batchJobRepository.save(batchJob);
  }

  private void buildIndustyBatchJob(BatchJob batchJob,
      List<UserInfo> userInfoList) {
    List<String> matchingUserInfoIds = userInfoList
        .stream()
        .filter(userInfo -> userInfo.getIndustry().equals(batchJob.getFromValue()))
        .map(userInfo -> userInfo.getUserId()).collect(Collectors.toList());
    batchJob.getUserInfoIds().addAll(matchingUserInfoIds);
    batchJobRepository.save(batchJob);
  }

  // ---------------------------- Property Methods -----------------------------
}
