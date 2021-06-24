package com.devcrawlers.conference.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devcrawlers.conference.management.model.ConferenceDetails;

@Repository
public interface ConferenceDetailsRepository extends MongoRepository<ConferenceDetails, Integer> {

	public List<ConferenceDetails> findByStatus(String status);

	public List<ConferenceDetails> findByConferencesId(int conferenceId);

	public List<ConferenceDetails> findByConferencesNameContaining(String conferenceName);

	public List<ConferenceDetails> findByConferencesYear(String conferenceYear);

	public Optional<ConferenceDetails> findByTopic(String topic);
	
	public List<ConferenceDetails> findByCreatedUser(String createdUser);

	public Optional<ConferenceDetails> findByTopicAndIdNotIn(String topic, int id);

	public Optional<ConferenceDetails> findByIdAndStatus(int id, String status);

}
