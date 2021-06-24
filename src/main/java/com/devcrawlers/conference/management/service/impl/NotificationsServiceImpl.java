package com.devcrawlers.conference.management.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.model.Notifications;
import com.devcrawlers.conference.management.repository.NotificationsRepository;
import com.devcrawlers.conference.management.service.NotificationsService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class NotificationsServiceImpl implements NotificationsService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private NotificationsRepository notificationsRepository;
	
	private int generateId() {
		List<Notifications> notificationsList = notificationsRepository.findAll();
		List<Integer> notificationsIdList = new ArrayList<>();
		
		for(Notifications notificationsObject : notificationsList) {
			notificationsIdList.add(notificationsObject.getId());
		}
		
		return IdGenerator.generateIDs(notificationsIdList);	
	}

	@Override
	public List<Notifications> findByUserName(String userName) {
		return notificationsRepository.findByUserName(userName);
	}

	@Override
	public List<Notifications> findByUserNameAndType(String userName, String type) {
		return notificationsRepository.findByUserNameAndType(userName, type);
	}

	@Override
	public void saveNotification(String userName, String type, String description, String remarks, String status) {
		
		Notifications notifications = new Notifications();
		notifications.setId(generateId());
		notifications.setUserName(userName);
		notifications.setType(type);
		notifications.setDescription(description);
		notifications.setRemarks(remarks);
		notifications.setCreatedDate(new Date());
		notifications.setStatus(status);
		notificationsRepository.save(notifications);
	}

	@Override
	public String deleteNotifications(int id) {
		
		Optional<Notifications> isPresentNotification = notificationsRepository.findById(id);
		if (!isPresentNotification.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		notificationsRepository.deleteById(id);
		return environment.getProperty("message.deleted");
	}

}
