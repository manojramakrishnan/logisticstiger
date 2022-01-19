package com.tigerlogistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigerlogistics.dao.RepositoryAdminMapper;
import com.tigerlogistics.model.RepositoryAdmin;
import com.tigerlogistics.service.RepositoryAdminManageService;



@Service
public class RepositoryAdminManageServiceImpl implements RepositoryAdminManageService {

	@Autowired
	private RepositoryAdminMapper repositoryAdminMapper;
	
	@Override
	public Map<String, Object> selectByID(Integer repositoryAdminId) {
		// TODO Auto-generated method stub
		Map<String,Object> resultSet= new HashMap<>();
		List<RepositoryAdmin> repositoryAdmins= new ArrayList<>();
		long total =0;
		RepositoryAdmin repositoryAdmin= new RepositoryAdmin();
		try {
			repositoryAdmin=repositoryAdminMapper.selectById(repositoryAdminId);
		}
		catch(PersistenceException e) {
			
		}
		
		if(repositoryAdmin != null) {
			repositoryAdmins.add(repositoryAdmin);
			total=1;
			
		}
		resultSet.put("data", repositoryAdmins);
		resultSet.put("total", total);
		return resultSet;
	}

}
