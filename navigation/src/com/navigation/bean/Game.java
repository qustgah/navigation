package com.navigation.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * ��Ϸ
 *
 */
public class Game implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	//��Ϸ����
	private String name;
	//�̵�
	private Store store;
	//���
	private Category category;
	//������
	private ReleaseMan releaseMan;
	//����ʱ��
	private Date releasetime;
	//�汾
	private String version;
	//����
	private String description;
	//��Ϸ��С
	private String size;
	//�Ƽ���־
	private String flag;
	//������
	private String loadnum;
	//���ص�ַ
	private String loadurl;
	private String belong;
	 
	public Game(int id,String name,Store store, Category category,ReleaseMan releaseMan,Date releasetime,String version,String description,String size,String flag,String loadnum,
			String loadurl,String belong){
		 this.id = id;
		 this.name = name ;
		 this.store = store;
		 this.category = category;
		 this.releaseMan = releaseMan;
		 this.releasetime = releasetime;
		 this.version = version;
		 this.description = description;
		 this.size = size;
		 this.flag = flag;
		 this.loadnum = loadnum;
		 this.loadurl = loadurl;
		 this.belong = belong;
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
	 
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLoadnum() {
		return loadnum;
	}
	public void setLoadnum(String loadnum) {
		this.loadnum = loadnum;
	}
	public String getLoadurl() {
		return loadurl;
	}
	public void setLoadurl(String loadurl) {
		this.loadurl = loadurl;
	}
	public ReleaseMan getReleaseMan() {
		return releaseMan;
	}
	public void setReleaseMan(ReleaseMan releaseMan) {
		this.releaseMan = releaseMan;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	
	public Game() {
		// TODO Auto-generated constructor stub
	}
	
	
}
