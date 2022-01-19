package com.tigerlogistics.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import com.tigerlogistics.exception.SystemLogServiceException;
import com.tigerlogistics.model.UserInfoDTO;
import com.tigerlogistics.service.SystemLogService;

public class SessionListener extends SessionListenerAdapter {
	
	@Autowired
	private SystemLogService systemLogService;
	
	@Override
	public void onStart(Session session) {
		super.onStart(session);
	}
	
	@Override
	public void onStop(Session session) {
		super.onStop(session);
		sessionDestroyedLog(session);

	}
	@Override
	public void onExpiration(Session session) {
		super.onExpiration(session);
		sessionDestroyedLog(session);
	}
	
	private void sessionDestroyedLog(Session session) {
		UserInfoDTO userInfo =(UserInfoDTO) session.getAttribute("userInfo");
		
		if(userInfo !=null) {
			try {
				systemLogService.insertAccessRecord(userInfo.getUserID(),userInfo.getUserName(),userInfo.getAccessIP(),SystemLogService.ACCESS_TYPE_LOGOUT);
			}
			catch(SystemLogServiceException e) {
				
			}
		}
	}
}
