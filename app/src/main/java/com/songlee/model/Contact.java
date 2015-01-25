package com.songlee.model;

public class Contact {
	private int id;
	private String number=null;       //���
	private String name=null;         //����
	private String phone=null;        //�ֻ���
	private String email=null;        //����
	private String address=null;      //��ַ
	private String gender=null;       //�Ա�
	private String relationship=null; //��ϵ
	private String remark=null;       //��ע
	//���캯��
	public Contact(){
		id=0;
		number="";
		name="";
		phone="";
		email="";
		address="";
		gender="";
		relationship="";
		remark="";
	}
	//��ȡ��������ϵ��id
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	//��ȡ��������ϵ�˱��
	public void setNumber(String number){
		this.number = number;
	}
	public String getNumber(){
		return number;
	}
	//��ȡ��������ϵ������
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	//��ȡ��������ϵ���ֻ���
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPhone(){
		return phone;
	}
	//��ȡ��������ϵ��Email
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}
	//��ȡ��������ϵ�˵�ַ
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return address;
	}
	//��ȡ��������ϵ���Ա�
	public void setGender(String gender){
		this.gender = gender;
	}
	public String getGender(){
		return gender;
	}
	//��ȡ��������ϵ�˹�ϵ
	public void setRelationship(String relationship){
		this.relationship = relationship;
	}
	public String getRelationship(){
		return relationship;
	}
	//��ȡ��������ϵ�˱�ע
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return remark;
	}
}
