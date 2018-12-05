package cn.itcast.util;

import java.util.List;

public class Result {
	private Integer code;
	private List list;
	
	public Result success(Integer code,List list) {
		this.setCode(code);
		this.setList(list);
		return this;
	}
	public Result fail(Integer code) {
		this.setCode(code);
		return this;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Result(Integer code, List list) {
		super();
		this.code = code;
		this.list = list;
	}
	public Result() {
		super();
	}
	
	
	
}
