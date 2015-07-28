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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoUpdaterTest {

///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  
  public BatchJob INDUSTRY_BATCH_JOB = new BatchJob("12", "SALES", "DOORS", Lists.newArrayList("789def"), BatchJobType.INDUSTRY);
  
  
  public BatchJob JOB_TITLE_BATCH_JOB = new BatchJob("12", "Cheif Executive Officor", "CEO", Lists.newArrayList("789def"), BatchJobType.JOB_TITIE);
  
  
  public UserInfo eduUserInfo = new UserInfo("12345abc", "SUSAN", "Cheif Executive Officor", "EDUCATION", 1L);

  
  public UserInfo salesUserInfoResult = new UserInfo("789def", "CHRIS", "Cheif Executive Officor", "SALES", 1L);
  public UserInfo salesUserInfo = mock(UserInfo.class);
  
  
  public CreateUserInfoDto salesUserInfoUpdate = new CreateUserInfoDto("CHRIS", "CEO", "DOORS", 1L);
  
////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Mock
  private UserInfoService userInfoService =  mock(UserInfoService.class); 
  
  
/////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//
//  @Test
//  public void testUpdateIndustry() {
//    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(INDUSTRY_BATCH_JOB, userInfoService);
//    when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
//    userInfoUpdater.run();
//    
//    verify(userInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(CreateUserInfoDto.class));
//  }
  
  @Test
  public void testException() {
    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(INDUSTRY_BATCH_JOB, userInfoService);
    when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
    stub(salesUserInfo.getUserId()).toReturn("789def");
    stub(salesUserInfo.createUserInfoDto()).toReturn(salesUserInfoUpdate);
    stub(userInfoService.update("789def", salesUserInfoUpdate)).toThrow(new RuntimeException()).toReturn(salesUserInfoResult);
    userInfoUpdater.run();
    verify(userInfoService, Mockito.times(2)).update("789def", salesUserInfoUpdate);
  }
  
//  @Test
//  public void testUpdateJob() {
//    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(JOB_TITLE_BATCH_JOB, userInfoService);
//    
//    when(userInfoService.getUserInfo("12345abc")).thenReturn(eduUserInfo);
//    when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
//    userInfoUpdater.run();
//    
//    
//  }
//  
  
  
//  @Test
//  public void testUpdateJob2() {
//    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(JOB_TITLE_BATCH_JOB, userInfoService);
//    UserInfo userInfoSpy = Mockito.spy(salesUserInfo);
//    
//    Mockito.when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
//    userInfoUpdater.run();
//    Mockito.when(userInfoSpy.createUserInfoDto()).thenReturn(salesUserInfoUpdate);
//    Mockito.verify(userInfoService, Mockito.times(1)).update("789def", salesUserInfoUpdate);
//  }
  
  
//  @Test
//  public void testCannotWrite() {
//    UserInfoUpdater userInfoUpdater = new UserInfoUpdater(JOB_TITLE_BATCH_JOB, userInfoService);
//    UserInfoUpdater userInfoUpdaterSpy = Mockito.spy(userInfoUpdater);
//    Mockito.when(userInfoUpdaterSpy.createUserInfoDto(salesUserInfo)).thenReturn(salesUserInfoUpdate);
//    Mockito.when(userInfoService.getUserInfo("12345abc")).thenReturn(eduUserInfo);
//    Mockito.when(userInfoService.getUserInfo("789def")).thenReturn(salesUserInfo);
//   
//   
//    
//    userInfoUpdater.run();
//    //Mockito.when(userInfoUpdater.createUserInfoDto(salesUserInfo)).thenReturn(salesUserInfoUpdate);
//
//    Mockito.when(userInfoService.update("789def", salesUserInfoUpdate)).thenThrow(RuntimeException.class);
//    
//    
//   // Mockito.verify(userInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(CreateUserInfoDto.class));
//  }
  
  
//------------------------ Implements:

//------------------------ Overrides:

//---------------------------- Abstract Methods -----------------------------

//---------------------------- Utility Methods ------------------------------

//---------------------------- Property Methods -----------------------------
  
}
