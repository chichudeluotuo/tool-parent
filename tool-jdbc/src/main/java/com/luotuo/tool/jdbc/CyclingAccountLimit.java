package com.luotuo.tool.jdbc;

import java.util.Date;

public class CyclingAccountLimit{

	private long customerId;
    private long creditLimit;
    private long availableLimit;
    private Date created;
    private Date modified;
    private int version;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(long creditLimit) {
		this.creditLimit = creditLimit;
	}
	public long getAvailableLimit() {
		return availableLimit;
	}
	public void setAvailableLimit(long availableLimit) {
		this.availableLimit = availableLimit;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "CyclingAccountLimit [customerId=" + customerId + ", creditLimit=" + creditLimit + ", availableLimit="
				+ availableLimit + ", created=" + created + ", modified=" + modified + ", version=" + version + "]";
	}

}
