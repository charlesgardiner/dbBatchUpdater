/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.services;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;
import com.charles.data.mongo.BatchJobRepository;
import com.charles.data.mongo.UserInfoRepository;
import com.google.common.collect.Lists;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoServiceTest {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @InjectMocks
  private UserInfoService userInfoService; 
  
  @Mock
  private BatchJobRepository batchJobRepository;
  
  @Mock
  private UserInfoRepository userInfoRepository;
  
  private CreateUserInfoDto createUserInfoDto = mock(CreateUserInfoDto.class);
    
  @Before
  public void initMocks(){
    MockitoAnnotations.initMocks(this);
  }

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Test
  public void testCreateUserInfoWithNoBatchJobs() {
    
    stub(createUserInfoDto.getIndustry()).toReturn("SALES");
    stub(createUserInfoDto.getVersion()).toReturn(1L);
    stub(createUserInfoDto.getName()).toReturn("Chrsdfasdfis");
    stub(createUserInfoDto.getJobTitle()).toReturn("CEO");
    UserInfo savedUserInfo = new UserInfo("567", "Chris", "CEO", "SALES", 1L);
    when(userInfoRepository.save(Mockito.any(UserInfo.class))).thenReturn(savedUserInfo);
    List<BatchJob> batchJobs = Lists.newArrayList();
    stub(batchJobRepository.findByFromValueAndBatchJobType("CEO", BatchJobType.JOB_TITLE)).toReturn(batchJobs);
    stub(batchJobRepository.findByFromValueAndBatchJobType("SALES", BatchJobType.INDUSTRY)).toReturn(batchJobs);


    userInfoService.createUserInfo(createUserInfoDto);
    
    verify(batchJobRepository, never()).save(batchJobs);
  }
  
  
  @Test
  public void testCreateUserInfoWithOneIndustryBatchJobs() {
    
    stub(createUserInfoDto.getIndustry()).toReturn("SALES");
    stub(createUserInfoDto.getVersion()).toReturn(1L);
    stub(createUserInfoDto.getName()).toReturn("Chrsdfasdfis");
    stub(createUserInfoDto.getJobTitle()).toReturn("CEO");
    UserInfo savedUserInfo = new UserInfo("567", "Chris", "CEO", "SALES", 1L);
    when(userInfoRepository.save(Mockito.any(UserInfo.class))).thenReturn(savedUserInfo);
    
    BatchJob industryBatchJob =  new BatchJob("123", "SALES", "DOORS", Lists.newArrayList(), BatchJobType.INDUSTRY);
    
    List<BatchJob> industryBatchJobs = Lists.newArrayList(industryBatchJob);
    List<BatchJob> jobTitleBatchJobs = Lists.newArrayList();
    stub(batchJobRepository.findByFromValueAndBatchJobType("CEO", BatchJobType.JOB_TITLE)).toReturn(jobTitleBatchJobs);
    stub(batchJobRepository.findByFromValueAndBatchJobType("SALES", BatchJobType.INDUSTRY)).toReturn(industryBatchJobs);

    userInfoService.createUserInfo(createUserInfoDto);
    
    verify(batchJobRepository, times(1)).save(Mockito.anyListOf(BatchJob.class));
  }
  
  @Test
  public void testCreateUserInfoWithOneJobTitleBatchJobs() {
    
    stub(createUserInfoDto.getIndustry()).toReturn("SALES");
    stub(createUserInfoDto.getVersion()).toReturn(1L);
    stub(createUserInfoDto.getName()).toReturn("Chrsdfasdfis");
    stub(createUserInfoDto.getJobTitle()).toReturn("CEO");
    UserInfo savedUserInfo = new UserInfo("567", "Chris", "CEO", "SALES", 1L);
    when(userInfoRepository.save(Mockito.any(UserInfo.class))).thenReturn(savedUserInfo);
    
    BatchJob jobTitleBatchJob =  new BatchJob("123", "CEO", "BOSS", Lists.newArrayList(), BatchJobType.INDUSTRY);
    
    List<BatchJob> industryBatchJobs = Lists.newArrayList();
    List<BatchJob> jobTitleBatchJobs = Lists.newArrayList(jobTitleBatchJob);
    stub(batchJobRepository.findByFromValueAndBatchJobType("CEO", BatchJobType.JOB_TITLE)).toReturn(jobTitleBatchJobs);
    stub(batchJobRepository.findByFromValueAndBatchJobType("SALES", BatchJobType.INDUSTRY)).toReturn(industryBatchJobs);

    userInfoService.createUserInfo(createUserInfoDto);
    
    verify(batchJobRepository, times(1)).save(Mockito.anyListOf(BatchJob.class));
  }
  
  
  @Test
  public void testCreateUserInfoWithTwoeBatchJobs() {
    
    stub(createUserInfoDto.getIndustry()).toReturn("SALES");
    stub(createUserInfoDto.getVersion()).toReturn(1L);
    stub(createUserInfoDto.getName()).toReturn("Chrsdfasdfis");
    stub(createUserInfoDto.getJobTitle()).toReturn("CEO");
    UserInfo savedUserInfo = new UserInfo("567", "Chris", "CEO", "SALES", 1L);
    when(userInfoRepository.save(Mockito.any(UserInfo.class))).thenReturn(savedUserInfo);
    
    BatchJob jobTitleBatchJob =  new BatchJob("123", "CEO", "BOSS", Lists.newArrayList(), BatchJobType.INDUSTRY);
    BatchJob industryBatchJob =  new BatchJob("123", "SALES", "DOORS", Lists.newArrayList(), BatchJobType.INDUSTRY);
    
    List<BatchJob> industryBatchJobs = Lists.newArrayList(industryBatchJob);
    List<BatchJob> jobTitleBatchJobs = Lists.newArrayList(jobTitleBatchJob);
    stub(batchJobRepository.findByFromValueAndBatchJobType("CEO", BatchJobType.JOB_TITLE)).toReturn(jobTitleBatchJobs);
    stub(batchJobRepository.findByFromValueAndBatchJobType("SALES", BatchJobType.INDUSTRY)).toReturn(industryBatchJobs);

    userInfoService.createUserInfo(createUserInfoDto);
    
    verify(batchJobRepository, times(2)).save(Mockito.anyListOf(BatchJob.class));
  }
  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
  
}
