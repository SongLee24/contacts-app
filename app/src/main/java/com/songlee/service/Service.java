package com.songlee.service;

import java.util.List;

import com.songlee.dataaccess.DBOperation;
import com.songlee.model.Contact;

import android.content.Context;

public class Service {
	
	private DBOperation dao=null;
	
	//���캯��
	public Service(Context context){
		dao = new DBOperation(context);
	}
	
	//������ϵ�˵ķ���
	public boolean save(Contact contact){
		boolean flag = dao.save(contact);
		return flag;
	}
	
	//��ѯ��ϵ�˷���
	public List getByName(String queryName){
		List list = dao.getByName(queryName);
		return list;
	}
	
	//ID��ѯ
	public Contact getById(int id){
		Contact contact = dao.getById(id);
		return contact;
	}
	
	//�޸���ϵ�˷���
	public boolean update(Contact contact){
		boolean flag = dao.update(contact);
		return flag;
	}
	
	//ɾ����ϵ�˷���
	public void delete(int id){
		dao.delete(id);
	}
}
