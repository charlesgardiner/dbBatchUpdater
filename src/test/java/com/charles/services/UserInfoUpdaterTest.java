package com.charles.services;

/*
 * © Copyright 2015 -  Charles Gardiner
 */

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



@RunWith(MockitoJUnitRunner.class)
public class UserInfoUpdaterTest {

///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  
  public BatchJob INDUSTRY_BATCH_JOB = new BatchJob("12", "SALES", "DOORS", Lists.newArrayList("789def"), BatchJobType.INDUSTRY);
  
  
  public BatchJob JOB_TITLE_BATCH_JOB = new BatchJob("12", "Cheif Executive Officor", "CEO", Lists.newArrayList("12345abc", "789def"), BatchJobType.JOB_TITIE);
  
  
  public UserInfo eduUserInfo = new UserInfo("12345abc", "SUSAN", "Cheif Executive Officor", "EDUCATION", 1L);

  
  public UserInfo salesUserInfo = new UserInfo("789def", "CHRIS", "Cheif Executive Officor", "SALES", 1L);
  
  
  public CreateUserInfoDto salesUserInfoUpdate = new CreateUserInfoDto("CHRIS", "Cheif Executive Officer", "DOORS", 1L);
  
////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Mock
  private UserInfoService userInfoService; 
  
  
/////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Test
  public void testUpdateIndustry() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(INDUSTRY_BATCH_JOB, userInfoService);
    Mockito.when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
    userInfoUpdater.run();
    
    Mockito.verify(userInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(CreateUserInfoDto.class));
  }
  
  @Test
  public void testUpdateJob() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(JOB_TITLE_BATCH_JOB, userInfoService);
    Mockito.when(userInfoService.getUserInfo("12345abc")).thenReturn(eduUserInfo);
    Mockito.when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
    userInfoUpdater.run();
    
    Mockito.verify(userInfoService, Mockito.times(2)).update(Mockito.anyString(), Mockito.any(CreateUserInfoDto.class));
  }
  
  @Test
  public void testCannotWrite() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(JOB_TITLE_BATCH_JOB, userInfoService);
    Mockito.when(userInfoService.getUserInfo("12345abc")).thenReturn(eduUserInfo);
    Mockito.when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
    Mockito.when(userInfoUpdater.createUserInfoDto(salesUserInfo)).thenReturn(salesUserInfoUpdate);
    Mockito.when(userInfoService.update("789def", salesUserInfoUpdate)).thenThrow(RuntimeException.class);
    
    userInfoUpdater.run();
    
    
    
    Mockito.verify(userInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(CreateUserInfoDto.class));
  }
  
  
//------------------------ Implements:

//------------------------ Overrides:

//---------------------------- Abstract Methods -----------------------------

//---------------------------- Utility Methods ------------------------------

//---------------------------- Property Methods -----------------------------
  
}
