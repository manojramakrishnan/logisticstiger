package com.tigerlogistics.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tigerlogistics.model.AppUser;

@Repository
@Transactional(readOnly=true)
public interface AppUserRepository extends JpaRepository<AppUser,Long>{

	Optional<AppUser> findByEmail(String email);
	
}
