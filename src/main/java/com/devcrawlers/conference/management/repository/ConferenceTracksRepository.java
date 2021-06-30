package com.devcrawlers.conference.management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.ConferenceTracks;

@Repository
public interface ConferenceTracksRepository extends MongoRepository<ConferenceTracks, Integer> {

	public List<ConferenceTracks> findByStatus(String status);

	public List<ConferenceTracks> findByNameContaining(String name);
	
	public Optional<ConferenceTracks> findByName(String name);

	public Optional<ConferenceTracks> findByNameAndIdNotIn(String name, int id);

	public Optional<ConferenceTracks> findByIdAndStatus(int id, String status);
}
