package com.tigerlogistics.service;

import java.time.LocalDateTime;
import java.util.Optional;

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
	
	public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
