package com.devcrawlers.conference.management.service.impl;

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
import com.devcrawlers.conference.management.model.KeynoteSpeakers;
import com.devcrawlers.conference.management.repository.KeynoteSpeakersRepository;
import com.devcrawlers.conference.management.resource.KeynoteSpeakersAddResource;
import com.devcrawlers.conference.management.resource.KeynoteSpeakersUpdateResource;
import com.devcrawlers.conference.management.service.KeynoteSpeakersService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class KeynoteSpeakersServiceImpl implements KeynoteSpeakersService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private KeynoteSpeakersRepository keynoteSpeakersRepository;
	
	private int generateId() {
		List<KeynoteSpeakers> keynoteSpeakersList = keynoteSpeakersRepository.findAll();
		List<Integer> keynoteSpeakersIdList = new ArrayList<>();
		
		for(KeynoteSpeakers keynoteSpeakersObject : keynoteSpeakersList) {
			keynoteSpeakersIdList.add(keynoteSpeakersObject.getId());
		}
		
		return IdGenerator.generateIDs(keynoteSpeakersIdList);	
	}
	
	@Override
	public List<KeynoteSpeakers> findAll() {
		return keynoteSpeakersRepository.findAll();
	}

	@Override
	public Optional<KeynoteSpeakers> findById(int id) {
		Optional<KeynoteSpeakers> keynoteSpeakers = keynoteSpeakersRepository.findById(id);
		if (keynoteSpeakers.isPresent()) {
			return Optional.ofNullable(keynoteSpeakers.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<KeynoteSpeakers> findByStatus(String status) {
		return keynoteSpeakersRepository.findByStatus(status);
	}

	@Override
	public List<KeynoteSpeakers> findByName(String name) {
		return keynoteSpeakersRepository.findByNameContaining(name);
	}

	@Override
	public Integer saveKeynoteSpeaker(KeynoteSpeakersAddResource keynoteSpeakersAddResource) {
		KeynoteSpeakers keynoteSpeakers = new KeynoteSpeakers();
		
		Optional<KeynoteSpeakers> isPresentKeynoteSpeaker = keynoteSpeakersRepository.findByName(keynoteSpeakersAddResource.getName());
        if (isPresentKeynoteSpeaker.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("keynoteSpeaker.duplicate"), "message");
		}
		
        keynoteSpeakers.setId(generateId());
        keynoteSpeakers.setName(keynoteSpeakersAddResource.getName());
        keynoteSpeakers.setDescription(keynoteSpeakersAddResource.getDescription());
        keynoteSpeakers.setDesignation(keynoteSpeakersAddResource.getDesignation());
        keynoteSpeakers.setImageURL(keynoteSpeakersAddResource.getImageURL());
        keynoteSpeakers.setStatus(CommonStatus.ACTIVE.toString());
        keynoteSpeakersRepository.save(keynoteSpeakers);
		return keynoteSpeakers.getId();
	}

	@Override
	public KeynoteSpeakers updateKeynoteSpeaker(int id, KeynoteSpeakersUpdateResource keynoteSpeakersUpdateResource) {
		Optional<KeynoteSpeakers> isPresentKeynoteSpeaker = keynoteSpeakersRepository.findById(id);
		if (!isPresentKeynoteSpeaker.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<KeynoteSpeakers> isPresentKeynoteSpeakerByName = keynoteSpeakersRepository.findByNameAndIdNotIn(keynoteSpeakersUpdateResource.getName(), id);
		if (isPresentKeynoteSpeakerByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("conference.duplicate"), "message");
		
		KeynoteSpeakers keynoteSpeakers = isPresentKeynoteSpeaker.get();
		keynoteSpeakers.setName(keynoteSpeakersUpdateResource.getName());
        keynoteSpeakers.setDescription(keynoteSpeakersUpdateResource.getDescription());
        keynoteSpeakers.setDesignation(keynoteSpeakersUpdateResource.getDesignation());
        keynoteSpeakers.setImageURL(keynoteSpeakersUpdateResource.getImageURL());
        keynoteSpeakers.setStatus(keynoteSpeakersUpdateResource.getStatus());
        keynoteSpeakersRepository.save(keynoteSpeakers);
		return keynoteSpeakers;
	}

	@Override
	public String deleteKeynoteSpeaker(int id) {
		Optional<KeynoteSpeakers> isPresentKeynoteSpeaker = keynoteSpeakersRepository.findById(id);
		if (!isPresentKeynoteSpeaker.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		keynoteSpeakersRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

}
