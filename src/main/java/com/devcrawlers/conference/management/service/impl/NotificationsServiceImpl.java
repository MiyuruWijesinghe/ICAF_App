package com.devcrawlers.conference.management.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.service.NotificationsService;

@Component
@Transactional(rollbackFor=Exception.class)
public class NotificationsServiceImpl implements NotificationsService {

}
