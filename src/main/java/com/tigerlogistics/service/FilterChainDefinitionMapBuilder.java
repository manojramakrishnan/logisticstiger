package com.tigerlogistics.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tigerlogistics.dao.RolePermissionMapper;
import com.tigerlogistics.model.RolePermissionDO;

public class FilterChainDefinitionMapBuilder {

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	private  StringBuilder builder =new StringBuilder();
	
	public LinkedHashMap<String,String> builderFilterChainDefinitionMap(){
		LinkedHashMap<String,String> permissionMap= new LinkedHashMap();
		
		permissionMap.put("/css/**", "anon");
        permissionMap.put("/js/**", "anon");
        permissionMap.put("/fonts/**", "anon");
        permissionMap.put("/media/**", "anon");
        permissionMap.put("/pagecomponent/**", "anon");
        permissionMap.put("/login", "anon, kickOut");
        permissionMap.put("/account/login", "anon");
        permissionMap.put("/account/checkCode/**", "anon");

        // 可变化的权限配置
        LinkedHashMap<String, String> permissions = getPermissionDataFromDB();
        if (permissions != null){
            permissionMap.putAll(permissions);
        }

        // 其余权限配置
        permissionMap.put("/", "authc");

//        permissionMap.forEach((s, s2) -> {System.out.println(s + ":" + s2);});

        return permissionMap;
	}
	
	private LinkedHashMap<String,String> getPermissionDataFromDB(){
		LinkedHashMap<String,String> permissionData=null;
		List<RolePermissionDO> rolePermissionDos= rolePermissionMapper.selectAll();
		if(rolePermissionDos != null) {
			permissionData = new LinkedHashMap<>(rolePermissionDos.size());
			String url;
			String role;
			String permission;
			for(RolePermissionDO rolePermissionDo : rolePermissionDos ) {
				url = rolePermissionDo.getUrl();
				role = rolePermissionDo.getRole();
				if(permissionData.containsKey(url)) {
					builder.delete(0, builder.length());
					builder.append(permissionData.get(url));
					builder.insert(builder.length() - 1, "," );
					builder.insert(builder.length() - 1, role );
				}
				else {
					builder.delete(0, builder.length());
                    builder.append("authc,kickOut, roles[" ).append(role).append("]");
				}
				permission = builder.toString();
				permissionData.put(url, permission);
			}
		}
		return permissionData;
	}
}
