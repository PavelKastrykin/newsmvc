package com.epam.newsportal.newsservice.entity.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLES")
class RoleDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*@Id
	@Column(name = "USER_ID")
	private long userId;*/
	
	@Id
	@Column(name = "ROLE")
	//@Enumerated(EnumType.STRING)
	private String roleName;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private UserDTO user;
	
	public RoleDTO() {}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
}
