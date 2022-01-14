package com.tigerlogistics.service;

import com.tigerlogistics.exception.SystemLogServiceException;

public interface SystemLogService {

	String ACCESS_TYPE_LOGIN = "login";
    String ACCESS_TYPE_LOGOUT = "logout";
    void insertAccessRecord(Integer userID, String userName, String accessIP, String accessType) throws SystemLogServiceException;
}
