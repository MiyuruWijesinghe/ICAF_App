package com.devcrawlers.conference.management.service;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.resource.AdminDashboardResponse;
import com.devcrawlers.conference.management.resource.ReviewerDashboardResponse;

@Service
public interface DashboardService {

	public AdminDashboardResponse getAdminDashboardDetails();
	
	public ReviewerDashboardResponse getReviewerDashboardDetails();
	
}
