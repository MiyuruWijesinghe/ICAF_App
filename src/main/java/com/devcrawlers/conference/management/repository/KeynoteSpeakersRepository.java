package com.devcrawlers.conference.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devcrawlers.conference.management.model.KeynoteSpeakers;

@Repository
public interface KeynoteSpeakersRepository extends MongoRepository<KeynoteSpeakers, Integer> {

	public List<KeynoteSpeakers> findByStatus(String status);

	public List<KeynoteSpeakers> findByNameContaining(String name);
	
	public Optional<KeynoteSpeakers> findByName(String name);

	public Optional<KeynoteSpeakers> findByNameAndIdNotIn(String name, int id);
}
