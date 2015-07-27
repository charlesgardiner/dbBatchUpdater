/*
 * © Copyright 2015 -  SourceClear Inc
 */


package com.charles.services;

import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;
import com.charles.data.mongo.UserInfoRepository;
import com.charles.data.dto.UserInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserInfoService {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private UserInfoRepository userInfoRepository;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  public UserInfo createUserInfo(CreateUserInfoDto userInfoDto) {
    UserInfo userInfo = new UserInfo();
    BeanUtils.copyProperties(userInfoDto, userInfo);
    return userInfoRepository.save(userInfo);
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
    throw new RuntimeException("User does not exist.");
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

