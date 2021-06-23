package com.devcrawlers.conference.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.Conference;

/**
 * Conference Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ConferenceRepository extends MongoRepository<Conference, Integer> {

	public List<Conference> findByNameContaining(String name);

	public List<Conference> findByStatus(String status);

	public Optional<Conference> findByName(String name);
	
	public List<Conference> findByYear(String year);

	public Optional<Conference> findByNameAndIdNotIn(String name, int id);

	public Optional<Conference> findByIdAndStatus(int id, String name);

}