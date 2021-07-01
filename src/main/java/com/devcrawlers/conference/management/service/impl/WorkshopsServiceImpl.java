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
import com.devcrawlers.conference.management.model.Workshops;
import com.devcrawlers.conference.management.repository.ConferenceTracksRepository;
import com.devcrawlers.conference.management.repository.WorkshopsRepository;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.WorkshopsAddResource;
import com.devcrawlers.conference.management.resource.WorkshopsUpdateResource;
import com.devcrawlers.conference.management.security.jwt.AuthTokenFilter;
import com.devcrawlers.conference.management.service.NotificationsService;
import com.devcrawlers.conference.management.service.WorkshopsService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class WorkshopsServiceImpl implements WorkshopsService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private WorkshopsRepository workshopsRepository;
	
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
		List<Workshops> workshopsList = workshopsRepository.findAll();
		List<Integer> workshopsIdList = new ArrayList<>();
		
		for(Workshops workshopsObject : workshopsList) {
			workshopsIdList.add(workshopsObject.getId());
		}
		
		return IdGenerator.generateIDs(workshopsIdList);	
	}
	
	@Override
	public List<Workshops> findAll() {
		return workshopsRepository.findAll();
	}

	@Override
	public Optional<Workshops> findById(int id) {
		Optional<Workshops> workshops = workshopsRepository.findById(id);
		if (workshops.isPresent()) {
			return Optional.ofNullable(workshops.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Workshops> findByStatus(String status) {
		return workshopsRepository.findByStatus(status);
	}

	@Override
	public List<Workshops> findByName(String name) {
		return workshopsRepository.findByNameContaining(name);
	}
	
	@Override
	public List<Workshops> findByConferenceTracksId(int conferenceTracksId) {
		return workshopsRepository.findByConferenceTrackId(conferenceTracksId);
	}

	@Override
	public List<Workshops> findByConferenceTracksName(String conferenceTracksName) {
		return workshopsRepository.findByConferenceTrackName(conferenceTracksName);
	}

	@Override
	public List<Workshops> findByCreatedUser(String createdUser) {
		return workshopsRepository.findByCreatedUser(createdUser);
	}

	@Override
	public Integer saveWorkshop(WorkshopsAddResource workshopsAddResource) {
		
		Workshops workshops = new Workshops();
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findByName(workshopsAddResource.getName());
        if (isPresentWorkshop.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("workshop.duplicate"), "message");
		}
        
        Optional<ConferenceTracks> conferenceTracks = conferenceTracksRepository.findByIdAndStatus(Integer.parseInt(workshopsAddResource.getConferenceTracksId()), CommonStatus.APPROVED.toString());
		if (!conferenceTracks.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("conference-tracks.invalid-value"), "message");
		} else {
			workshops.setConferenceTrack(conferenceTracks.get());
		}
		
		workshops.setId(generateId());
		workshops.setName(workshopsAddResource.getName());
		workshops.setDescription(workshopsAddResource.getDescription());
		workshops.setDocumentURL(workshopsAddResource.getDocumentURL());
		workshops.setConductor(workshopsAddResource.getConductor());
		workshops.setImageURL(workshopsAddResource.getImageURL());
		workshops.setVenue(workshopsAddResource.getVenue());
		workshops.setDate(workshopsAddResource.getDate());
		workshops.setStartTime(workshopsAddResource.getStartTime());
		workshops.setEndTime(workshopsAddResource.getEndTime());
		workshops.setStatus(CommonStatus.PENDING.toString());
		workshops.setCreatedUser(authTokenFilter.getUsername());
		workshops.setCreatedDate(formatDate(new Date()));
		workshopsRepository.save(workshops);
		return workshops.getId();
	}

	@Override
	public Workshops updateWorkshop(int id, WorkshopsUpdateResource workshopsUpdateResource) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Workshops> isPresentWorkshopByName = workshopsRepository.findByNameAndIdNotIn(workshopsUpdateResource.getName(), id);
		if (isPresentWorkshopByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("workshop.duplicate"), "message");
		
		Workshops workshops = isPresentWorkshop.get();
		workshops.setName(workshopsUpdateResource.getName());
		workshops.setDescription(workshopsUpdateResource.getDescription());
		workshops.setDocumentURL(workshopsUpdateResource.getDocumentURL());
		workshops.setConductor(workshopsUpdateResource.getConductor());
		workshops.setImageURL(workshopsUpdateResource.getImageURL());
		workshops.setVenue(workshopsUpdateResource.getVenue());
		workshops.setDate(workshopsUpdateResource.getDate());
		workshops.setStartTime(workshopsUpdateResource.getStartTime());
		workshops.setEndTime(workshopsUpdateResource.getEndTime());
		workshops.setStatus(CommonStatus.PENDING.toString());
		workshops.setCreatedUser(authTokenFilter.getUsername());
		workshops.setCreatedDate(formatDate(new Date()));
		workshops.setRemarks(null);
		workshops.setApprovedUser(null);
		workshops.setApprovedDate(null);
		workshops.setRejectedUser(null);
		workshops.setRejectedDate(null);
		workshopsRepository.save(workshops);
		return workshops;
	}

	@Override
	public String deleteWorkshop(int id) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		workshopsRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

	@Override
	public String approveWorkshop(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Workshops workshops = isPresentWorkshop.get();
		workshops.setStatus(CommonStatus.APPROVED.toString());
		workshops.setRemarks(commonApproveRejectResource.getRemarks());
		workshops.setApprovedUser(authTokenFilter.getUsername());
		workshops.setApprovedDate(formatDate(new Date()));
		workshops.setRejectedUser(null);
		workshops.setRejectedDate(null);
		workshopsRepository.save(workshops);
		
		notificationsService.saveNotification(workshops.getCreatedUser(), "Workshop Details", environment.getProperty("message-workshop.approved"), workshops.getRemarks(), CommonStatus.APPROVED.toString());
		
		return environment.getProperty("common.approved");
	}

	@Override
	public String rejectWorkshop(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Workshops workshops = isPresentWorkshop.get();
		workshops.setStatus(CommonStatus.REJECTED.toString());
		workshops.setRemarks(commonApproveRejectResource.getRemarks());
		workshops.setRejectedUser(authTokenFilter.getUsername());
		workshops.setRejectedDate(formatDate(new Date()));
		workshops.setApprovedUser(null);
		workshops.setApprovedDate(null);
		workshopsRepository.save(workshops);
		
		notificationsService.saveNotification(workshops.getCreatedUser(), "Workshop Details", environment.getProperty("message-workshop.rejected"), workshops.getRemarks(), CommonStatus.REJECTED.toString());
		
		return environment.getProperty("common.rejected");
	}

}
