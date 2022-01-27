package com.tigerlogistics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tigerlogistics.request.RegistrationRequest;
import com.tigerlogistics.service.RegistrationService;

@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@PostMapping
	public String register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}

	public RegistrationController(RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}
	
	@GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
	
	
}
