/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;
import com.google.common.collect.Lists;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoUpdaterTest {

///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  
  public BatchJob INDUSTRY_BATCH_JOB = new BatchJob("12", "SALES", "DOORS", Lists.newArrayList("789def"), BatchJobType.INDUSTRY);
  
  public BatchJob JOB_TITLE_BATCH_JOB = new BatchJob("12", "Cheif Executive Officor", "CEO", Lists.newArrayList("789def"), BatchJobType.JOB_TITLE);
  
  public UserInfo salesUserInfo = mock(UserInfo.class);
  
  public UserInfo salesUserInfoResult = new UserInfo("789def", "CHRIS", "Cheif Executive Officor", "SALES", 1L);
    
  public CreateUserInfoDto salesUserIndustryInfoUpdate = new CreateUserInfoDto("CHRIS", "Cheif Executive Officor", "DOORS", 1L);
  
  public CreateUserInfoDto salesUserJobTitleInfoUpdate = new CreateUserInfoDto("CHRIS", "CEO", "SALES", 1L);
  
  
////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Mock
  private UserInfoService userInfoService =  mock(UserInfoService.class); 
  
  
/////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Test
  public void testUpdateIndustry() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(INDUSTRY_BATCH_JOB, userInfoService, 10);
    stub(userInfoService.getUserInfo("789def")).toReturn(salesUserInfo);
    stub(salesUserInfo.getUserId()).toReturn("789def");
    stub(salesUserInfo.createUserInfoDto()).toReturn(salesUserIndustryInfoUpdate);
    stub(userInfoService.update("789def", salesUserIndustryInfoUpdate)).toReturn(salesUserInfoResult);
    userInfoUpdater.run();
    
    verify(userInfoService, Mockito.times(1)).update("789def", salesUserIndustryInfoUpdate);
  }
  
  @Test
  public void testUpdateJob() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(JOB_TITLE_BATCH_JOB, userInfoService, 10);
    stub(userInfoService.getUserInfo("789def")).toReturn(salesUserInfo);
    stub(salesUserInfo.getUserId()).toReturn("789def");
    stub(salesUserInfo.createUserInfoDto()).toReturn(salesUserJobTitleInfoUpdate);
    stub(userInfoService.update("789def", salesUserIndustryInfoUpdate)).toReturn(salesUserInfoResult);
    userInfoUpdater.run();
    
    verify(userInfoService, Mockito.times(1)).update("789def", salesUserJobTitleInfoUpdate);
  }
  
  
  @Test
  public void testOneException() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(INDUSTRY_BATCH_JOB, userInfoService, 10);
    stub(userInfoService.getUserInfo("789def")).toReturn(salesUserInfo);
    stub(salesUserInfo.getUserId()).toReturn("789def");
    stub(salesUserInfo.createUserInfoDto()).toReturn(salesUserIndustryInfoUpdate);
    stub(userInfoService.update("789def", salesUserIndustryInfoUpdate)).toThrow(new RuntimeException())
      .toReturn(salesUserInfoResult);
    userInfoUpdater.run();
    
    verify(userInfoService, Mockito.times(2)).update("789def", salesUserIndustryInfoUpdate);
  }
  
  @Test
  public void testTwoExceptions() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(INDUSTRY_BATCH_JOB, userInfoService, 10);
    stub(userInfoService.getUserInfo("789def")).toReturn(salesUserInfo);
    stub(salesUserInfo.getUserId()).toReturn("789def");
    stub(salesUserInfo.createUserInfoDto()).toReturn(salesUserIndustryInfoUpdate);
    stub(userInfoService.update("789def", salesUserIndustryInfoUpdate)).toThrow(new RuntimeException())
      .toThrow(new RuntimeException())
      .toReturn(salesUserInfoResult);
    userInfoUpdater.run();
    
    verify(userInfoService, Mockito.times(3)).update("789def", salesUserIndustryInfoUpdate);
  }
  

  
//------------------------ Implements:

//------------------------ Overrides:

//---------------------------- Abstract Methods -----------------------------

//---------------------------- Utility Methods ------------------------------

//---------------------------- Property Methods -----------------------------
  
}
