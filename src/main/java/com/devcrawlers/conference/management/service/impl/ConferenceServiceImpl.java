package com.devcrawlers.conference.management.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.devcrawlers.conference.management.repository.ConferenceRepository;
import com.devcrawlers.conference.management.resource.ConferenceAddResource;
import com.devcrawlers.conference.management.resource.ConferenceUpdateResource;
import com.devcrawlers.conference.management.service.ConferenceService;
import com.devcrawlers.conference.management.util.IdGenerator;

/**
 * Conference Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class ConferenceServiceImpl implements ConferenceService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ConferenceRepository conferenceRepository;
	
	private int generateId() {
		List<Conference> conferenceList = conferenceRepository.findAll();
		List<Integer> conferenceIdList = new ArrayList<>();
		
		for(Conference conferenceObject : conferenceList) {
			conferenceIdList.add(conferenceObject.getId());
		}
		
		return IdGenerator.generateIDs(conferenceIdList);	
	}
	
	@Override
	public List<Conference> findAll() {
		return conferenceRepository.findAll();
	}

	@Override
	public Optional<Conference> findById(int id) {
		Optional<Conference> conference = conferenceRepository.findById(id);
		if (conference.isPresent()) {
			return Optional.ofNullable(conference.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<Conference> findByStatus(String status) {
		return conferenceRepository.findByStatus(status);
	}
	
	@Override
	public List<Conference> findByName(String name) {
		return conferenceRepository.findByNameContaining(name);
	}
	
	@Override
	public List<Conference> findByYear(String year) {
		return conferenceRepository.findByYear(year);
	}

	@Override
	public Integer saveConference(ConferenceAddResource conferenceAddResource) {
		Conference conference = new Conference();
		
		Optional<Conference> isPresentConference = conferenceRepository.findByName(conferenceAddResource.getName());
        if (isPresentConference.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("conference.duplicate"), "message");
		}
		
		conference.setId(generateId());
		conference.setYear(conferenceAddResource.getYear());
		conference.setName(conferenceAddResource.getName());
		conference.setDescription(conferenceAddResource.getDescription());
		conference.setVenue(conferenceAddResource.getVenue());
		conference.setPayment(new BigDecimal(conferenceAddResource.getPayment()));
		conference.setStatus(CommonStatus.ACTIVE.toString());
		conferenceRepository.save(conference);
		return conference.getId();
	}

	@Override
	public Conference updateConference(int id, ConferenceUpdateResource conferenceUpdateResource) {
		Optional<Conference> isPresentConference = conferenceRepository.findById(id);
		if (!isPresentConference.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Conference> isPresentConferenceByName = conferenceRepository.findByNameAndIdNotIn(conferenceUpdateResource.getName(), id);
		if (isPresentConferenceByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("conference.duplicate"), "message");
		
		Conference conference = isPresentConference.get();
		conference.setYear(conferenceUpdateResource.getYear());
		conference.setName(conferenceUpdateResource.getName());
		conference.setDescription(conferenceUpdateResource.getDescription());
		conference.setVenue(conferenceUpdateResource.getVenue());
		conference.setPayment(new BigDecimal(conferenceUpdateResource.getPayment()));
		conference.setStatus(conferenceUpdateResource.getStatus());
		conferenceRepository.save(conference);
		return conference;
	}

	@Override
	public String deleteConference(int id) {
		Optional<Conference> isPresentConference = conferenceRepository.findById(id);
		if (!isPresentConference.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		conferenceRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

}
