package com.tigerlogistics.model;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tigerlogistics.appuser.AppUserRole;

@Entity
public class AppUser implements UserDetails {

	
	
	@SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
	@Id
	@GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
	private long id;
	private String email;
	private String name;
	private String password;
	private String userName;
	@Enumerated(EnumType.STRING)
	private AppUserRole appUserRole;
	private Boolean locked;
	private Boolean enable;
	
	@Override
	public String toString() {
		return "AppUser [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", userName="
				+ userName + ", appUserRole=" + appUserRole + ", locked=" + locked + ", enable=" + enable + "]";
	}

	public AppUser(long id, String email, String name, String password, String userName, AppUserRole appUserRole,
			Boolean locked, Boolean enable) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.userName = userName;
		this.appUserRole = appUserRole;
		this.locked = locked;
		this.enable = enable;
	}

	public AppUser() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AppUserRole getAppUserRole() {
		return appUserRole;
	}

	public void setAppUserRole(AppUserRole appUserRole) {
		this.appUserRole = appUserRole;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enable;
	}

	
	
}
