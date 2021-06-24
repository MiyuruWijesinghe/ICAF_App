package com.devcrawlers.conference.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.model.Notifications;

@Service
public interface NotificationsService {

	public List<Notifications> findAll();
}
