package com.devcrawlers.conference.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.enums.CommonStatus;
import com.devcrawlers.conference.management.repository.ConferenceDetailsRepository;
import com.devcrawlers.conference.management.repository.ConferenceRepository;
import com.devcrawlers.conference.management.repository.ConferenceTracksRepository;
import com.devcrawlers.conference.management.repository.KeynoteSpeakersRepository;
import com.devcrawlers.conference.management.repository.ResearchRepository;
import com.devcrawlers.conference.management.repository.RolesRepository;
import com.devcrawlers.conference.management.repository.UserRepository;
import com.devcrawlers.conference.management.repository.WorkshopsRepository;
import com.devcrawlers.conference.management.resource.AdminDashboardResponse;
import com.devcrawlers.conference.management.resource.ResearcherDashboardResponse;
import com.devcrawlers.conference.management.resource.ReviewerDashboardResponse;
import com.devcrawlers.conference.management.resource.WorkshopConductorDashboardResponse;
import com.devcrawlers.conference.management.service.DashboardService;

@Component
@Transactional(rollbackFor=Exception.class)
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private Environment environment;

	@Autowired
	private ConferenceRepository conferenceRepository;
	
	@Autowired
	private ConferenceDetailsRepository conferenceDetailsRepository;
	
	@Autowired
	private ConferenceTracksRepository conferenceTracksRepository;
	
	@Autowired
	private KeynoteSpeakersRepository keynoteSpeakersRepository;
	
	@Autowired
	private ResearchRepository researchRepository;
	
	@Autowired
	private WorkshopsRepository workshopsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Override
	public AdminDashboardResponse getAdminDashboardDetails() {
		
		AdminDashboardResponse adminDashboardResponse = new AdminDashboardResponse();
		
		Long totalConferences = conferenceRepository.count();
		Long totalConferenceDetails = conferenceDetailsRepository.count();
		Long totalTracks = conferenceTracksRepository.count();
		Long totalKeynoteSpeakers = keynoteSpeakersRepository.count();
		Long totalResearches = researchRepository.count();
		Long totalWorkshops = workshopsRepository.count();
		Long totalUsers = userRepository.count();
		Long totalRoles = rolesRepository.count();
		
		adminDashboardResponse.setTotalConferences(totalConferences.toString());
		adminDashboardResponse.setTotalConferenceDetails(totalConferenceDetails.toString());
		adminDashboardResponse.setTotalTracks(totalTracks.toString());
		adminDashboardResponse.setTotalKeynoteSpeakers(totalKeynoteSpeakers.toString());
		adminDashboardResponse.setTotalResearches(totalResearches.toString());
		adminDashboardResponse.setTotalWorkshops(totalWorkshops.toString());
		adminDashboardResponse.setTotalUsers(totalUsers.toString());
		adminDashboardResponse.setTotalRoles(totalRoles.toString());
		
		return adminDashboardResponse;
	}

	@Override
	public ReviewerDashboardResponse getReviewerDashboardDetails() {
		
		ReviewerDashboardResponse reviewerDashboardResponse = new ReviewerDashboardResponse();
		
		Long totalPendingResearches = researchRepository.countByStatus(CommonStatus.PENDING.toString());
		Long totalApprovedResearches = researchRepository.countByStatus(CommonStatus.APPROVED.toString());
		Long totalRejectedResearches = researchRepository.countByStatus(CommonStatus.REJECTED.toString());
		Long totalPendingWorkshops = workshopsRepository.countByStatus(CommonStatus.PENDING.toString());
		Long totalApprovedWorkshops = workshopsRepository.countByStatus(CommonStatus.APPROVED.toString());
		Long totalRejectedWorkshops = workshopsRepository.countByStatus(CommonStatus.REJECTED.toString());
		
		reviewerDashboardResponse.setTotalPendingResearches(totalPendingResearches.toString());
		reviewerDashboardResponse.setTotalApprovedResearches(totalApprovedResearches.toString());
		reviewerDashboardResponse.setTotalRejectedResearches(totalRejectedResearches.toString());
		reviewerDashboardResponse.setTotalPendingWorkshops(totalPendingWorkshops.toString());
		reviewerDashboardResponse.setTotalApprovedWorkshops(totalApprovedWorkshops.toString());
		reviewerDashboardResponse.setTotalRejectedWorkshops(totalRejectedWorkshops.toString());
		
		return reviewerDashboardResponse;
	}

	@Override
	public ResearcherDashboardResponse getResearcherDashboardDetails(String user) {
		
		ResearcherDashboardResponse researcherDashboardResponse = new ResearcherDashboardResponse();
		
		Long totalPendingResearches = researchRepository.countByStatusAndCreatedUser(CommonStatus.PENDING.toString(), user);
		Long totalApprovedResearches = researchRepository.countByStatusAndCreatedUser(CommonStatus.APPROVED.toString(), user);
		Long totalRejectedResearches = researchRepository.countByStatusAndCreatedUser(CommonStatus.REJECTED.toString(), user);
		
		researcherDashboardResponse.setTotalPendingResearches(totalPendingResearches.toString());
		researcherDashboardResponse.setTotalApprovedResearches(totalApprovedResearches.toString());
		researcherDashboardResponse.setTotalRejectedResearches(totalRejectedResearches.toString());
		
		return researcherDashboardResponse;
	}

	@Override
	public WorkshopConductorDashboardResponse getWorkshopConductorDashboardDetails(String user) {
		
		WorkshopConductorDashboardResponse workshopConductorDashboardResponse = new WorkshopConductorDashboardResponse();
		
		Long totalPendingWorkshops = workshopsRepository.countByStatusAndCreatedUser(CommonStatus.PENDING.toString(), user);
		Long totalApprovedWorkshops = workshopsRepository.countByStatusAndCreatedUser(CommonStatus.APPROVED.toString(), user);
		Long totalRejectedWorkshops = workshopsRepository.countByStatusAndCreatedUser(CommonStatus.REJECTED.toString(), user);
		
		workshopConductorDashboardResponse.setTotalPendingWorkshops(totalPendingWorkshops.toString());
		workshopConductorDashboardResponse.setTotalApprovedWorkshops(totalApprovedWorkshops.toString());
		workshopConductorDashboardResponse.setTotalRejectedWorkshops(totalRejectedWorkshops.toString());
		
		return workshopConductorDashboardResponse;
	}
	
}
