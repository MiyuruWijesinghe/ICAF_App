package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devcrawlers.conference.management.model.ConferenceDetails;
import com.devcrawlers.conference.management.resource.ConferenceDetailsAddResource;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ConferenceDetailsUpdateResource;

@Service
public interface ConferenceDetailsService {

	public List<ConferenceDetails> findAll();
	
	public Optional<ConferenceDetails> findById(int id);
	
	public List<ConferenceDetails> findByStatus(String status);
	
	public List<ConferenceDetails> findByConferenceId(int conferenceId);
	
	public List<ConferenceDetails> findByConferenceName(String conferenceName);
	
	public List<ConferenceDetails> findByConferenceYear(String conferenceYear);
	
	public List<ConferenceDetails> findByCreatedUser(String createdUser);
	
	public Integer saveConferenceDetails(ConferenceDetailsAddResource conferenceDetailsAddResource);
	
	public ConferenceDetails updateConferenceDetails(int id, ConferenceDetailsUpdateResource conferenceDetailsUpdateResource);
	
	public String deleteConferenceDetails(int id);
	
	public String approveConferenceDetails(int id, CommonApproveRejectResource commonApproveRejectResource);

	public String rejectConferenceDetails(int id, CommonApproveRejectResource commonApproveRejectResource);
}
