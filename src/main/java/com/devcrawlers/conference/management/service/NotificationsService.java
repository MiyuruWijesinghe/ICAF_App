package com.devcrawlers.conference.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.model.Notifications;

@Service
public interface NotificationsService {

	public List<Notifications> findByUserName(String userName);
	
	public List<Notifications> findByUserNameAndType(String userName, String type);
	
	public void saveNotification(String userName, String type, String description, String remarks, String status);
	
	public String deleteNotifications(int id);
	
}
