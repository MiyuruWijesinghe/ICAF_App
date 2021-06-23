package com.devcrawlers.conference.management.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
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
import com.devcrawlers.conference.management.model.Conference;
import com.devcrawlers.conference.management.model.ConferenceDetails;
import com.devcrawlers.conference.management.repository.ConferenceDetailsRepository;
import com.devcrawlers.conference.management.repository.ConferenceRepository;
import com.devcrawlers.conference.management.resource.ConferenceDetailsAddResource;
import com.devcrawlers.conference.management.resource.ConferenceDetailsApproveRejectResource;
import com.devcrawlers.conference.management.resource.ConferenceDetailsUpdateResource;
import com.devcrawlers.conference.management.service.ConferenceDetailsService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class ConferenceDetailsServiceImpl implements ConferenceDetailsService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ConferenceRepository conferenceRepository;
	
	@Autowired
	private ConferenceDetailsRepository conferenceDetailsRepository;
	
	
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	private int generateId() {
		List<ConferenceDetails> conferenceDetailsList = conferenceDetailsRepository.findAll();
		List<Integer> conferenceDetailsIdList = new ArrayList<>();
		
		for(ConferenceDetails conferenceDetailsObject : conferenceDetailsList) {
			conferenceDetailsIdList.add(conferenceDetailsObject.getId());
		}
		
		return IdGenerator.generateIDs(conferenceDetailsIdList);	
	}
	
	@Override
	public List<ConferenceDetails> findAll() {
		return conferenceDetailsRepository.findAll();
	}

	@Override
	public Optional<ConferenceDetails> findById(int id) {
		Optional<ConferenceDetails> conferenceDetails = conferenceDetailsRepository.findById(id);
		if (conferenceDetails.isPresent()) {
			return Optional.ofNullable(conferenceDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ConferenceDetails> findByStatus(String status) {
		return conferenceDetailsRepository.findByStatus(status);
	}

	@Override
	public List<ConferenceDetails> findByConferenceId(int conferenceId) {
		return conferenceDetailsRepository.findByConferencesId(conferenceId);
	}

	@Override
	public List<ConferenceDetails> findByConferenceName(String conferenceName) {
		return conferenceDetailsRepository.findByConferencesNameContaining(conferenceName);
	}

	@Override
	public List<ConferenceDetails> findByConferenceYear(String conferenceYear) {
		return conferenceDetailsRepository.findByConferencesYear(conferenceYear);
	}

	@Override
	public Integer saveConferenceDetails(ConferenceDetailsAddResource conferenceDetailsAddResource) {
		
		ConferenceDetails conferenceDetails = new ConferenceDetails();
		
		Optional<ConferenceDetails> isPresentConferenceDetails = conferenceDetailsRepository.findByTopic(conferenceDetailsAddResource.getTopic());
        if (isPresentConferenceDetails.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("conference-details.duplicate"), "message");
		}
		
        Optional<Conference> conference = conferenceRepository.findByIdAndStatus(Integer.parseInt(conferenceDetailsAddResource.getConferenceId()), CommonStatus.ACTIVE.toString());
		if (!conference.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("conference.invalid-value"), "message");
		} else {
			conferenceDetails.setConferences(conference.get());
		}
        
        conferenceDetails.setId(generateId());
        conferenceDetails.setTopic(conferenceDetailsAddResource.getTopic());
        conferenceDetails.setDescription(conferenceDetailsAddResource.getDescription());
        conferenceDetails.setConductor(conferenceDetailsAddResource.getConductor());
		conferenceDetails.setVenue(conferenceDetailsAddResource.getVenue());
		conferenceDetails.setDate(formatDate(conferenceDetailsAddResource.getDate()));
		conferenceDetails.setStartTime(conferenceDetailsAddResource.getStartTime());
		conferenceDetails.setEndTime(conferenceDetailsAddResource.getEndTime());
		conferenceDetails.setPayment(new BigDecimal(conferenceDetailsAddResource.getPayment()));
		conferenceDetails.setStatus(CommonStatus.PENDING.toString());
		conferenceDetails.setCreatedUser("MKW");
		conferenceDetails.setCreatedDate(new Date());
		conferenceDetailsRepository.save(conferenceDetails);
		return conferenceDetails.getId();
	}

	@Override
	public ConferenceDetails updateConferenceDetails(int id, ConferenceDetailsUpdateResource conferenceDetailsUpdateResource) {
		
		Optional<ConferenceDetails> isPresentConferenceDetails = conferenceDetailsRepository.findById(id);
		if (!isPresentConferenceDetails.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<ConferenceDetails> isPresentConferenceDetailsByTopic = conferenceDetailsRepository.findByTopicAndIdNotIn(conferenceDetailsUpdateResource.getTopic(), id);
		if (isPresentConferenceDetailsByTopic.isPresent())
			throw new ValidateRecordException(environment.getProperty("conference-details.duplicate"), "message");
		
		ConferenceDetails conferenceDetails = isPresentConferenceDetails.get();
		conferenceDetails.setTopic(conferenceDetailsUpdateResource.getTopic());
        conferenceDetails.setDescription(conferenceDetailsUpdateResource.getDescription());
        conferenceDetails.setConductor(conferenceDetailsUpdateResource.getConductor());
		conferenceDetails.setVenue(conferenceDetailsUpdateResource.getVenue());
		conferenceDetails.setDate(formatDate(conferenceDetailsUpdateResource.getDate()));
		conferenceDetails.setStartTime(conferenceDetailsUpdateResource.getStartTime());
		conferenceDetails.setEndTime(conferenceDetailsUpdateResource.getEndTime());
		conferenceDetails.setPayment(new BigDecimal(conferenceDetailsUpdateResource.getPayment()));
		conferenceDetails.setStatus(CommonStatus.PENDING.toString());
		conferenceDetails.setCreatedUser("MKW");
		conferenceDetails.setCreatedDate(new Date());
		conferenceDetails.setRemarks(null);
		conferenceDetails.setApprovedUser(null);
		conferenceDetails.setApprovedDate(null);
		conferenceDetails.setRejectedUser(null);
		conferenceDetails.setRejectedDate(null);
		conferenceDetailsRepository.save(conferenceDetails);
		return conferenceDetails;
	}

	@Override
	public String deleteConferenceDetails(int id) {
		
		Optional<ConferenceDetails> isPresentConferenceDetails = conferenceDetailsRepository.findById(id);
		if (!isPresentConferenceDetails.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		conferenceDetailsRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

	@Override
	public String approveConferenceDetails(int id, ConferenceDetailsApproveRejectResource conferenceDetailsApproveRejectResource) {
		
		Optional<ConferenceDetails> isPresentConferenceDetails = conferenceDetailsRepository.findById(id);
		if (!isPresentConferenceDetails.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		ConferenceDetails conferenceDetails = isPresentConferenceDetails.get();
		conferenceDetails.setStatus(CommonStatus.APPROVED.toString());
		conferenceDetails.setRemarks(conferenceDetailsApproveRejectResource.getRemarks());
		conferenceDetails.setApprovedUser(conferenceDetailsApproveRejectResource.getUserName());
		conferenceDetails.setApprovedDate(new Date());
		conferenceDetails.setRejectedUser(null);
		conferenceDetails.setRejectedDate(null);
		conferenceDetailsRepository.save(conferenceDetails);
		
		return environment.getProperty("common.approved");
	}

	@Override
	public String rejectConferenceDetails(int id, ConferenceDetailsApproveRejectResource conferenceDetailsApproveRejectResource) {
		
		Optional<ConferenceDetails> isPresentConferenceDetails = conferenceDetailsRepository.findById(id);
		if (!isPresentConferenceDetails.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		ConferenceDetails conferenceDetails = isPresentConferenceDetails.get();
		conferenceDetails.setStatus(CommonStatus.REJECTED.toString());
		conferenceDetails.setRemarks(conferenceDetailsApproveRejectResource.getRemarks());
		conferenceDetails.setRejectedUser(conferenceDetailsApproveRejectResource.getUserName());
		conferenceDetails.setRejectedDate(new Date());
		conferenceDetails.setApprovedUser(null);
		conferenceDetails.setApprovedDate(null);
		conferenceDetailsRepository.save(conferenceDetails);
		
		return environment.getProperty("common.rejected");
	}

}
