package com.tigerlogistics.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.tigerlogistics.exception.UserInfoServiceException;
import com.tigerlogistics.model.UserInfoDTO;
import com.tigerlogistics.service.UserInfoService;
import com.tigerlogistics.util.MD5Util;

public class UserAuthorisingRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pricipalCollection) {
		Set<String> roles= new HashSet<>();
		Subject currentSubject= SecurityUtils.getSubject();
		Session session = currentSubject.getSession();
		UserInfoDTO userInfo =(UserInfoDTO) session.getAttribute("userInfo");
		roles.addAll(userInfo.getRole());
		return new SimpleAuthorizationInfo(roles);
	}

	

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String realmName= getName();
		String credentials = "";
		try {
			UsernamePasswordToken  userNamePasswordToken= (UsernamePasswordToken) token;
			String principal = userNamePasswordToken.getUsername();
			Integer userId= Integer.valueOf(principal);
			UserInfoDTO userInfoDTO = userInfoService.getUserInfo(userId);
			if(userInfoDTO != null) {
				Subject currentSubject = SecurityUtils.getSubject();
				Session session = currentSubject.getSession();
				session.setAttribute("userInfo", userInfoDTO);
				String checkCode = (String) session.getAttribute("checkCode");
				String password = userInfoDTO.getPassword();
				if(checkCode != null && password != null) {
					checkCode = checkCode.toUpperCase();
					credentials = MD5Util.MD5(password + checkCode);
				}
				userInfoDTO.setPassword(null);
			}
			return new SimpleAuthenticationInfo(principal,credentials,realmName);
			
			
		}
		catch(Exception e) {
			throw new AuthenticationException();
		}
	}
	
}
