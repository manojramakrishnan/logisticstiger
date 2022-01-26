package com.tigerlogistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tigerlogistics.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long>{

}
