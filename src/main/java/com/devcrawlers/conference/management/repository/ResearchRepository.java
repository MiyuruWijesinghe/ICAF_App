package com.devcrawlers.conference.management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.Research;

@Repository
public interface ResearchRepository extends MongoRepository<Research, Integer> {

	public List<Research> findByStatus(String status);

	public List<Research> findByNameContaining(String name);

	public List<Research> findByConferenceTrackId(int conferenceTracksId);

	public List<Research> findByConferenceTrackName(String conferenceTracksName);

	public List<Research> findByCreatedUser(String createdUser);

	public Optional<Research> findByName(String name);

	public Optional<Research> findByNameAndIdNotIn(String name, int id);
	
	public Long countByStatus(String status);
	
	public Long countByStatusAndCreatedUser(String status, String createdUser);
}
