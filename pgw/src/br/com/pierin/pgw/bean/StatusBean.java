package br.com.pierin.pgw.bean;

import java.io.Serializable;
import java.util.Date;

public class StatusBean implements Serializable, Comparable<StatusBean>{

	private static final long serialVersionUID = -7626082998135258101L;
	private Date changed_at;
	private String status;
	
	public Date getChanged_at() {
		return changed_at;
	}
	public void setChanged_at(Date changed_at) {
		this.changed_at = changed_at;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int compareTo(StatusBean o) {
		if (this.changed_at.before(o.getChanged_at())){
			return -1;
		}
		if (this.changed_at.after(o.getChanged_at())){
			return 1;
		}
		return 0;
	}

	
}
