package com.devcrawlers.conference.management.service;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.resource.AdminDashboardResponse;
import com.devcrawlers.conference.management.resource.ResearcherDashboardResponse;
import com.devcrawlers.conference.management.resource.ReviewerDashboardResponse;
import com.devcrawlers.conference.management.resource.WorkshopConductorDashboardResponse;

@Service
public interface DashboardService {

	public AdminDashboardResponse getAdminDashboardDetails();
	
	public ReviewerDashboardResponse getReviewerDashboardDetails();
	
	public ResearcherDashboardResponse getResearcherDashboardDetails(String user);
	
	public WorkshopConductorDashboardResponse getWorkshopConductorDashboardDetails(String user);

}
