package com.tigerlogistics.service;

import com.tigerlogistics.exception.UserInfoServiceException;
import com.tigerlogistics.model.UserInfoDTO;

public interface UserInfoService {

	UserInfoDTO getUserInfo(Integer userId) throws UserInfoServiceException;

}
