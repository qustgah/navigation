package com.navigation.bean;

import java.io.Serializable;

/**
 * �����ˣ���λ
 * @author Gaoahui
 *
 */
public class ReleaseMan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id; 
	/**	�����˻�λ���� */
	private String name ;
	 public ReleaseMan() {
		// TODO Auto-generated constructor stub
	}
	 public ReleaseMan(int id,String name) {
		 this.id = id;
		 this.name = name;
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
}
