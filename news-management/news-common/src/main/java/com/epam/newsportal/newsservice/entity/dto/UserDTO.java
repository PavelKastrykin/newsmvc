package com.epam.newsportal.newsservice.entity.dto;

import com.epam.newsportal.newsservice.entity.RoleName;
import com.epam.newsportal.newsservice.entity.User;

public class UserDTO {
	
	private long userId;
	private String userName;
	private String login;
	private String password;
	private RoleName roleName;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RoleName getRoleName() {
		return roleName;
	}
	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}
	
	public User buildDTOtoUser(){
		User user = new User();
		user.setUserId(this.getUserId());
		user.setUserName(this.getUserName());
		user.setLogin(this.getLogin());
		user.setPassword(this.getPassword());
		user.setRoleName(this.getRoleName());
		return user;
	}
	
	public void buildUserToDTO(User user){
		if (user != null) {
			this.setUserId(user.getUserId());
			this.setUserName(user.getUserName());
			this.setLogin(user.getLogin());
			this.setPassword(user.getPassword());
			this.setRoleName(user.getRoleName());
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roleName != other.roleName)
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userName=" + userName + ", login=" + login + ", password=" + password
				+ ", roleName=" + roleName + "]";
	}
	
	
}
