package com.huamengtong.wms.dto;


import com.huamengtong.wms.entity.main.TWmsUserEntity;

import java.io.Serializable;

public class SimpleUserDTO extends TWmsUserEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8105250201499234978L;

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
