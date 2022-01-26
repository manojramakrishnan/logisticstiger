package com.tigerlogistics.service;

import org.springframework.stereotype.Service;

import com.tigerlogistics.model.ConfirmationToken;
import com.tigerlogistics.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	public void saveConfirmationToken(ConfirmationToken confirmationToken) {
		// TODO Auto-generated method stub
		confirmationTokenRepository.save(confirmationToken);
	} 
}
