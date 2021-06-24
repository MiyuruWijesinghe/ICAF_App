package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.model.Research;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ResearchAddResource;
import com.devcrawlers.conference.management.resource.ResearchUpdateResource;

@Service
public interface ResearchService {

	public List<Research> findAll();
	
	public Optional<Research> findById(int id);
	
	public List<Research> findByStatus(String status);
	
	public List<Research> findByName(String name);
	
	public List<Research> findByConferenceDetailsId(int conferenceDetailsId);
	
	public List<Research> findByConferenceDetailsTopic(String conferenceDetailsTopic);
	
	public List<Research> findByCreatedUser(String createdUser);
	
	public Integer saveResearch(ResearchAddResource researchAddResource);
	
	public Research updateResearch(int id, ResearchUpdateResource researchUpdateResource);
	
	public String deleteResearch(int id);
	
	public String approveResearch(int id, CommonApproveRejectResource commonApproveRejectResource);

	public String rejectResearch(int id, CommonApproveRejectResource commonApproveRejectResource);
	
}
