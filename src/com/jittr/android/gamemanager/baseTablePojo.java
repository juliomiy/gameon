package com.jittr.android.gamemanager;

import java.sql.Timestamp;

public abstract class baseTablePojo {

	private String modifiedDate;
	private String createdDate;
	
	
	private String getCurrentTimestamp() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return ts.toString();
	}


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getCreatedDate() {
		if (createdDate == null)
			createdDate = getCurrentTimestamp();
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}  //class
