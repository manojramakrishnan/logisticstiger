package com.tigerlogistics.service;

import org.springframework.stereotype.Service;

import com.tigerlogistics.appuser.AppUserRole;
import com.tigerlogistics.email.EmailSender;
import com.tigerlogistics.email.EmailValidator;
import com.tigerlogistics.model.AppUser;
import com.tigerlogistics.request.RegistrationRequest;

@Service
public class RegistrationService {

	private final EmailValidator emailValidator;
	private final AppUserService appUserService;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailSender emailSender;
	
	public RegistrationService(EmailValidator emailValidator, AppUserService appUserService,
			ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
		super();
		this.emailValidator = emailValidator;
		this.appUserService = appUserService;
		this.confirmationTokenService = confirmationTokenService;
		this.emailSender = emailSender;
	}

	public String register(RegistrationRequest request) {
		// TODO Auto-generated method stub
		boolean isValidEmail = emailValidator.test(request.getEmail());
		if( !isValidEmail) {
			throw new IllegalStateException("emailNotValid");
		
		}
		AppUser appUser = new AppUser();
		appUser.setUserName(request.getFirstName());
		appUser.setName(request.getLastName());
		appUser.setEmail(request.getEmail());
		appUser.setPassword(request.getPassword());
		appUser.setAppUserRole(AppUserRole.USER);
		String token = appUserService.signUpUser((appUser));
				
				
		
		
		return null;
	}

}
