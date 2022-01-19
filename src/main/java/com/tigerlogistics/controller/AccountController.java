package com.tigerlogistics.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tigerlogistics.exception.RepositoryAdminManageServiceException;
import com.tigerlogistics.exception.SystemLogServiceException;
import com.tigerlogistics.model.RepositoryAdmin;
import com.tigerlogistics.model.UserInfoDTO;
import com.tigerlogistics.service.RepositoryAdminManageService;
import com.tigerlogistics.service.SystemLogService;
import com.tigerlogistics.util.Response;
import com.tigerlogistics.util.ResponseFactory;


@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
    private RepositoryAdminManageService repositoryAdminManageService;
	
	@Autowired
    private SystemLogService systemLogService;

	private static final String USER_ID = "id";
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "password";
	
	@RequestMapping (value="checkCode/{time}", method= RequestMethod.GET)
		public void getCheckCode(@PathVariable("time") String time, HttpServletResponse response, HttpServletRequest request) {
			BufferedImage checkCodeImage=null;
			String checkCodeString =null;
			Map<String, Object> checkCode = com.tigerlogistics.util.CaptchaGenerator.generateCaptcha();
			if (checkCode != null) {
	            checkCodeString = (String) checkCode.get("captchaString");
	            checkCodeImage = (BufferedImage) checkCode.get("captchaImage");
	       
			}
			if (checkCodeString != null && checkCodeImage != null) {
	            try (ServletOutputStream outputStream = response.getOutputStream()) {
	                
	                HttpSession session = request.getSession();
	                session.setAttribute("checkCode", checkCodeString);

	                
	                ImageIO.write(checkCodeImage, "png", outputStream);

	                response.setHeader("Pragma", "no-cache");
	                response.setHeader("Cache-Control", "no-cache");
	                response.setDateHeader("Expires", 0);
	                response.setContentType("image/png");
	            } catch (IOException e) {
//	                log.error("fail to get the ServletOutputStream");
	            }
	        }else{
//	        log.error("fail to gettttttt the ServletOutputStream");
	        }
		}
	
	@RequestMapping(value= "login",method=RequestMethod.POST)
	public
    @ResponseBody
    Map<String, Object> login(@RequestBody Map<String, Object> user) {
		Response response = ResponseFactory.newInstance();
        String result = Response.RESPONSE_RESULT_ERROR;
        String errorMsg = "";
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null && !currentUser.isAuthenticated()) {
            String id = (String) user.get(USER_ID);
            String password = (String) user.get(USER_PASSWORD);
            Session session = currentUser.getSession();
            UsernamePasswordToken token = new UsernamePasswordToken(id, password);

            try {
            	currentUser.login(token);
            	UserInfoDTO userInfo = (UserInfoDTO) session.getAttribute("userInfo");
            	userInfo.setAccessIP(session.getHost());
            	List<RepositoryAdmin> repositoryAdmin = (List<RepositoryAdmin>) repositoryAdminManageService.selectByID(userInfo.getUserID()).get("data");
                userInfo.setRepositoryBelong(-1);
                if (!repositoryAdmin.isEmpty()) {
                    Integer repositoryBelong = repositoryAdmin.get(0).getRepositoryBelongID();
                    if (repositoryBelong != null) {
                        userInfo.setRepositoryBelong(repositoryBelong);
                    }
                }
                systemLogService.insertAccessRecord(userInfo.getUserID(), userInfo.getUserName(),
                        userInfo.getAccessIP(), SystemLogService.ACCESS_TYPE_LOGIN);
                result = Response.RESPONSE_RESULT_SUCCESS;
            }
            catch (UnknownAccountException e) {
                errorMsg = "unknownAccount";
            } catch (IncorrectCredentialsException e) {
                errorMsg = "incorrectCredentials";
            } catch (AuthenticationException e) {
                errorMsg = "authenticationError";
                e.printStackTrace();
            } catch (SystemLogServiceException   e) {
                errorMsg = "ServerError";
            } finally {
                // 当登陆失败则清除session中的用户信息
                if (result.equals(Response.RESPONSE_RESULT_ERROR)){
                    session.setAttribute("userInfo", null);
                }
            }
        } else {
            errorMsg = "already login";
        }

        // 设置 Response
        response.setResponseResult(result);
        response.setResponseMsg(errorMsg);
        return response.generateResponse();
            
	}
	
	
	}

