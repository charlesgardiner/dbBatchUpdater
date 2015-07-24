/*
 * © Copyright 2015 -  SourceClear Inc
 */

package com.charles;

import com.charles.data.UserInfo;
import com.charles.data.dto.CreateUserInfoDto;
import com.charles.data.dto.UserInfoDto;
import com.charles.services.UserInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController(value = "/")
public class UserInfoController {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private UserInfoService userInfoService;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @RequestMapping(value = "userinfo", method = RequestMethod.POST)
  public ResponseEntity postUserInfo(@RequestBody CreateUserInfoDto userInfoDto) {
    UserInfo userInfo = userInfoService.createUserInfo(userInfoDto);
    return ResponseEntity.ok(UserInfoDto.fromUserInfo(userInfo));
  }

  @RequestMapping(value = "userinfo", method = RequestMethod.GET)
  public ResponseEntity getUserInfo(@RequestParam("page") Integer page){
    Page<UserInfo> userInfoPage = userInfoService.getPage(new PageRequest(page, 3));
    List<UserInfo> userInfoList = userInfoPage.getContent();
    return new ResponseEntity(userInfoList, HttpStatus.OK);
  }

  @RequestMapping(value = "userinfo/{id}", method = RequestMethod.GET)
  public ResponseEntity getUserInfo(@PathVariable String id){
    UserInfo userInfo = userInfoService.getUserInfo(id);
    return ResponseEntity.ok(UserInfoDto.fromUserInfo(userInfo));
  }

  @RequestMapping(value = "userinfo/{id}", method = RequestMethod.POST)
  public ResponseEntity editUserInfo(@PathVariable String id, @RequestBody CreateUserInfoDto userInfoDto){
    UserInfo userInfo = userInfoService.update(id, userInfoDto);
    return ResponseEntity.ok(UserInfoDto.fromUserInfo(userInfo));
  }

  @RequestMapping(value = "userinfo/{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteUserInfo(@PathVariable String id){
    userInfoService.delete(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }


  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
}
