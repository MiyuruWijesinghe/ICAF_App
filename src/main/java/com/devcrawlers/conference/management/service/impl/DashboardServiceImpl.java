package com.devcrawlers.conference.management.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.resource.AdminDashboardResponse;
import com.devcrawlers.conference.management.resource.ReviewerDashboardResponse;
import com.devcrawlers.conference.management.service.DashboardService;

@Component
@Transactional(rollbackFor=Exception.class)
public class DashboardServiceImpl implements DashboardService {

	@Override
	public AdminDashboardResponse getAdminDashboardDetails() {
		
		return null;
	}

	@Override
	public ReviewerDashboardResponse getReviewerDashboardDetails() {
		
		return null;
	}

	
	
}
