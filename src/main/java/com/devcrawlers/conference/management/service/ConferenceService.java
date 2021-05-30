package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.devcrawlers.conference.management.model.Conference;
import com.devcrawlers.conference.management.resource.ConferenceAddResource;
import com.devcrawlers.conference.management.resource.ConferenceUpdateResource;

/**
 * Conference Service
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ConferenceService {

	public List<Conference> findAll();
	
	public Optional<Conference> findById(int id);
	
	public List<Conference> findByStatus(String status);
	
	public List<Conference> findByName(String name);
	
	public Integer saveConference(ConferenceAddResource conferenceAddResource);
	
	public Conference updateConference(int id, ConferenceUpdateResource conferenceUpdateResource);
	
	public String deleteConference(int id);

}
