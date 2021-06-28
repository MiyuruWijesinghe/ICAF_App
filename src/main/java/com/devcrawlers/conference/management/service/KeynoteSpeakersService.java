package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.devcrawlers.conference.management.model.KeynoteSpeakers;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.KeynoteSpeakersAddResource;
import com.devcrawlers.conference.management.resource.KeynoteSpeakersUpdateResource;

@Service
public interface KeynoteSpeakersService {

	public List<KeynoteSpeakers> findAll();
	
	public Optional<KeynoteSpeakers> findById(int id);
	
	public List<KeynoteSpeakers> findByStatus(String status);
	
	public List<KeynoteSpeakers> findByName(String name);
	
	public Integer saveKeynoteSpeaker(KeynoteSpeakersAddResource keynoteSpeakersAddResource);
	
	public KeynoteSpeakers updateKeynoteSpeaker(int id, KeynoteSpeakersUpdateResource keynoteSpeakersUpdateResource);
	
	public String deleteKeynoteSpeaker(int id);
	
	public String approveKeynoteSpeaker(int id, CommonApproveRejectResource commonApproveRejectResource);

	public String rejectKeynoteSpeaker(int id, CommonApproveRejectResource commonApproveRejectResource);
	
}
