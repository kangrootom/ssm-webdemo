package cn.itcast.domain;

import java.util.Date;

public class EmpVo {
	private String ename;
	private Date begindate;
	private Date enddate;
	private Double comm;
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public Date getBegindate() {
		return begindate;
	}
	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Double getComm() {
		return comm;
	}
	public void setComm(Double comm) {
		this.comm = comm;
	}
	public EmpVo(String ename, Date begindate, Date enddate, Double comm) {
		super();
		this.ename = ename;
		this.begindate = begindate;
		this.enddate = enddate;
		this.comm = comm;
	}
	public EmpVo() {
		super();
	}
	
}
