package com.navigation.bean;

import java.io.Serializable;

/**
 * ��Ϸ���
 * @author Gaoahui
 *
 */
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	//��Ϸ�������
	private String name;
	//��Ϸ�Ƿ�����
	private int net;// 1 ����   0 ����
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
