package com.tigerlogistics.dao;

import org.springframework.stereotype.Component;

import com.tigerlogistics.model.RepositoryAdmin;

@Component
public interface RepositoryAdminMapper {

	RepositoryAdmin selectById(Integer id);
}
