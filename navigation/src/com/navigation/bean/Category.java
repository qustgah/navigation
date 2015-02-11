package com.navigation.bean;

import java.io.Serializable;

/**
 * 游戏类别
 * @author Gaoahui
 *
 */
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	//游戏类别名称
	private String name;
	//游戏是否联网
	private int net;// 1 联网   0 单机
	 public Category() {
		// TODO Auto-generated constructor stub
	}
	 public Category(int id,String name,int net){
		 this.id = id;
		 this.name = name;
		 this.net = net;
	 }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNet() {
		return net;
	}
	public void setNet(int net) {
		this.net = net;
	}
	
}
