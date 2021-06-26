package com.devcrawlers.conference.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.repository.RolesRepository;
import com.devcrawlers.conference.management.resource.AdminDashboardResponse;
import com.devcrawlers.conference.management.resource.ReviewerDashboardResponse;
import com.devcrawlers.conference.management.service.DashboardService;

@Component
@Transactional(rollbackFor=Exception.class)
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private Environment environment;

	@Autowired
	private RolesRepository rolesRepository;
	
	
	
	@Override
	public AdminDashboardResponse getAdminDashboardDetails() {
		
		AdminDashboardResponse adminDashboardResponse = new AdminDashboardResponse();
		
		Long totalRoles = rolesRepository.count();
		
		adminDashboardResponse.setTotalRoles(totalRoles.toString());
		
		return adminDashboardResponse;
	}

	@Override
	public ReviewerDashboardResponse getReviewerDashboardDetails() {
		
		return null;
	}

	
	
}
