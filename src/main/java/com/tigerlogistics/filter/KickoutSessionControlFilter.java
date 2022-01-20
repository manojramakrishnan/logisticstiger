package com.tigerlogistics.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class KickoutSessionControlFilter extends AccessControlFilter {
	
	private String kickOutUrl;
	private boolean kickOutAfter;
	private int maxSessionNum;
	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;
	public String getKickOutUrl() {
		return kickOutUrl;
	}
	public void setKickOutUrl(String kickOutUrl) {
		this.kickOutUrl = kickOutUrl;
	}
	public boolean isKickOutAfter() {
		return kickOutAfter;
	}
	public void setKickOutAfter(boolean kickOutAfter) {
		this.kickOutAfter = kickOutAfter;
	}
	public int getMaxSessionNum() {
		return maxSessionNum;
	}
	public void setMaxSessionNum(int maxSessionNum) {
		this.maxSessionNum = maxSessionNum;
	}
	public SessionManager getSessionManager() {
		return sessionManager;
	}
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	public Cache<String, Deque<Serializable>> getCache() {
		return cache;
	}
	public void setCache(Cache<String, Deque<Serializable>> cache) {
		this.cache = cache;
	}
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Subject subject = getSubject(request,response);
		
		if (!subject.isAuthenticated() && !subject.isRemembered())
            return true;

        // 判断当前用户登陆数量是否超出
        Session session = subject.getSession();
        String userName = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();

        // 初始化用户的登陆队列，将用户的队列放入到缓存中
        Deque<Serializable> deque = cache.get(userName);
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(userName, deque);
        }

        // 如果队列中没有此用户的 sessionId 且用户没有被踢出，则放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickOut") == null) {
            deque.push(sessionId);
        }

        // 若队列中的 sessionId 是否超出最大会话数目， 则踢出用户
        while (deque.size() > maxSessionNum) {
            Serializable kickOutSessionId;
            if (kickOutAfter) {
                kickOutSessionId = deque.removeFirst();
            } else {
                kickOutSessionId = deque.removeLast();
            }

            // 设置 sessionId 对应的 session 中的字段，表示该用户已经被踢出
            try {
                Session kickOutSession = sessionManager.getSession(new DefaultSessionKey(kickOutSessionId));
                if (kickOutSession != null) {
                    kickOutSession.setAttribute("kickOut", true);
                }
            } catch (Exception e) {
                // do logging
                e.printStackTrace();
            }
        }

        // 如果当前登陆用户被踢出，则退出并跳转
        if (session.getAttribute("kickOut") != null && Boolean.TRUE.equals(session.getAttribute("kickOut"))) {
            try {
                // 登出
                subject.logout();

                // 根据请求类型作出处理
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-with"))) {
                    // 普通请求
                    WebUtils.issueRedirect(httpServletRequest, httpServletResponse, kickOutUrl);
                } else {
                    // ajax 请求
                    httpServletResponse.setStatus(430);
                }

            } catch (Exception e) {
                // do nothing
            }
            return false;
        }

        return true;
    }



}
	
	

