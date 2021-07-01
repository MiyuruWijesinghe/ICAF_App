package com.devcrawlers.conference.management.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.enums.CommonStatus;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.exception.ValidateRecordException;
import com.devcrawlers.conference.management.model.ConferenceTracks;
import com.devcrawlers.conference.management.repository.ConferenceTracksRepository;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ConferenceTracksAddResource;
import com.devcrawlers.conference.management.resource.ConferenceTracksUpdateResource;
import com.devcrawlers.conference.management.security.jwt.AuthTokenFilter;
import com.devcrawlers.conference.management.service.ConferenceTracksService;
import com.devcrawlers.conference.management.service.NotificationsService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class ConferenceTracksServiceImpl implements ConferenceTracksService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ConferenceTracksRepository conferenceTracksRepository;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;
	
	private String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		return format.format(date);
	}
	
	private int generateId() {
		List<ConferenceTracks> conferenceTracksList = conferenceTracksRepository.findAll();
		List<Integer> conferenceTracksIdList = new ArrayList<>();
		
		for(ConferenceTracks conferenceTracksObject : conferenceTracksList) {
			conferenceTracksIdList.add(conferenceTracksObject.getId());
		}
		
		return IdGenerator.generateIDs(conferenceTracksIdList);	
	}
	
	@Override
	public List<ConferenceTracks> findAll() {
		return conferenceTracksRepository.findAll();
	}

	@Override
	public Optional<ConferenceTracks> findById(int id) {
		Optional<ConferenceTracks> conferenceTracks = conferenceTracksRepository.findById(id);
		if (conferenceTracks.isPresent()) {
			return Optional.ofNullable(conferenceTracks.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ConferenceTracks> findByStatus(String status) {
		return conferenceTracksRepository.findByStatus(status);
	}

	@Override
	public List<ConferenceTracks> findByName(String name) {
		return conferenceTracksRepository.findByNameContaining(name);
	}
	
	@Override
	public Integer saveConferenceTrack(ConferenceTracksAddResource conferenceTracksAddResource) {
		ConferenceTracks conferenceTrack = new ConferenceTracks();
		
		Optional<ConferenceTracks> isPresentConferenceTrack = conferenceTracksRepository.findByName(conferenceTracksAddResource.getName());
        if (isPresentConferenceTrack.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("track.duplicate"), "message");
		}
		
        conferenceTrack.setId(generateId());
        conferenceTrack.setName(conferenceTracksAddResource.getName());
        conferenceTrack.setImageURL(conferenceTracksAddResource.getImageURL());
		conferenceTrack.setStatus(CommonStatus.PENDING.toString());
		conferenceTrack.setCreatedUser(authTokenFilter.getUsername());
		conferenceTrack.setCreatedDate(formatDate(new Date()));
		conferenceTracksRepository.save(conferenceTrack);
		return conferenceTrack.getId();
	}

	@Override
	public ConferenceTracks updateConferenceTrack(int id, ConferenceTracksUpdateResource conferenceTracksUpdateResource) {
		Optional<ConferenceTracks> isPresentConferenceTrack = conferenceTracksRepository.findById(id);
		if (!isPresentConferenceTrack.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<ConferenceTracks> isPresentConferenceTrackByName = conferenceTracksRepository.findByNameAndIdNotIn(conferenceTracksUpdateResource.getName(), id);
		if (isPresentConferenceTrackByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("track.duplicate"), "message");
		
		ConferenceTracks conferenceTrack = isPresentConferenceTrack.get();
		conferenceTrack.setName(conferenceTracksUpdateResource.getName());
		conferenceTrack.setImageURL(conferenceTracksUpdateResource.getImageURL());
		conferenceTrack.setStatus(CommonStatus.PENDING.toString());
		conferenceTrack.setCreatedUser(authTokenFilter.getUsername());
		conferenceTrack.setCreatedDate(formatDate(new Date()));
		conferenceTrack.setRemarks(null);
		conferenceTrack.setApprovedUser(null);
		conferenceTrack.setApprovedDate(null);
		conferenceTrack.setRejectedUser(null);
		conferenceTrack.setRejectedDate(null);
		conferenceTracksRepository.save(conferenceTrack);
		return conferenceTrack;
	}

	@Override
	public String deleteConferenceTrack(int id) {
		Optional<ConferenceTracks> isPresentConferenceTrack = conferenceTracksRepository.findById(id);
		if (!isPresentConferenceTrack.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		conferenceTracksRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

	@Override
	public String approveConferenceTrack(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<ConferenceTracks> isPresentConferenceTrack = conferenceTracksRepository.findById(id);
		if (!isPresentConferenceTrack.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		ConferenceTracks conferenceTrack = isPresentConferenceTrack.get();
		conferenceTrack.setStatus(CommonStatus.APPROVED.toString());
		conferenceTrack.setRemarks(commonApproveRejectResource.getRemarks());
		conferenceTrack.setApprovedUser(authTokenFilter.getUsername());
		conferenceTrack.setApprovedDate(formatDate(new Date()));
		conferenceTrack.setRejectedUser(null);
		conferenceTrack.setRejectedDate(null);
		conferenceTracksRepository.save(conferenceTrack);
		
		notificationsService.saveNotification(conferenceTrack.getCreatedUser(), "Tracks", environment.getProperty("message-tracks.approved"), conferenceTrack.getRemarks(), CommonStatus.APPROVED.toString());
		
		return environment.getProperty("common.approved");
	}

	@Override
	public String rejectConferenceTrack(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<ConferenceTracks> isPresentConferenceTrack = conferenceTracksRepository.findById(id);
		if (!isPresentConferenceTrack.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		ConferenceTracks conferenceTrack = isPresentConferenceTrack.get();
		conferenceTrack.setStatus(CommonStatus.REJECTED.toString());
		conferenceTrack.setRemarks(commonApproveRejectResource.getRemarks());
		conferenceTrack.setRejectedUser(authTokenFilter.getUsername());
		conferenceTrack.setRejectedDate(formatDate(new Date()));
		conferenceTrack.setApprovedUser(null);
		conferenceTrack.setApprovedDate(null);
		conferenceTracksRepository.save(conferenceTrack);
		
		notificationsService.saveNotification(conferenceTrack.getCreatedUser(), "Tracks", environment.getProperty("message-tracks.rejected"), conferenceTrack.getRemarks(), CommonStatus.REJECTED.toString());
		
		return environment.getProperty("common.rejected");
	}
	
}
