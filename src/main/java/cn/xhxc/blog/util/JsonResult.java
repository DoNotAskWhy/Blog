package cn.xhxc.blog.util;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class JsonResult<T> implements Serializable{


	private Integer state;
	private String message;
	private T data;
	
	public JsonResult() {
		super();
	}
	
/*
 * 构造方法的参数变为异常，异常中获取message
 */
	public JsonResult(Throwable e) {
		super();
		this.message = e.getMessage();
	}

	public JsonResult(Integer state) {
		super();
		this.state = state;
	}

	
	public JsonResult(Integer state, T data) {
		super();
		this.state = state;
		this.data = data;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}