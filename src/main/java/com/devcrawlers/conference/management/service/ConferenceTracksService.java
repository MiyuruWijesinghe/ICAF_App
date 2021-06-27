package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.devcrawlers.conference.management.model.ConferenceTracks;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ConferenceTracksAddResource;
import com.devcrawlers.conference.management.resource.ConferenceTracksUpdateResource;

@Service
public interface ConferenceTracksService {

	public List<ConferenceTracks> findAll();
	
	public Optional<ConferenceTracks> findById(int id);
	
	public List<ConferenceTracks> findByStatus(String status);
	
	public List<ConferenceTracks> findByName(String name);
	
	public Integer saveConferenceTrack(ConferenceTracksAddResource conferenceTracksAddResource);
	
	public ConferenceTracks updateConferenceTrack(int id, ConferenceTracksUpdateResource conferenceTracksUpdateResource);
	
	public String deleteConferenceTrack(int id);
	
	public String approveConferenceTrack(int id, CommonApproveRejectResource commonApproveRejectResource);

	public String rejectConferenceTrack(int id, CommonApproveRejectResource commonApproveRejectResource);
}
