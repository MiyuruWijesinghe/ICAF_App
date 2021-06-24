package com.devcrawlers.conference.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.model.Notifications;
import com.devcrawlers.conference.management.repository.NotificationsRepository;
import com.devcrawlers.conference.management.service.NotificationsService;

@Component
@Transactional(rollbackFor=Exception.class)
public class NotificationsServiceImpl implements NotificationsService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private NotificationsRepository notificationsRepository;
	
	@Override
	public List<Notifications> findAll() {
		return notificationsRepository.findAll();
	}

	
}
