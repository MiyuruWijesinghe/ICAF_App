package com.devcrawlers.conference.management.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.Workshops;

@Repository
public interface WorkshopsRepository extends MongoRepository<Workshops, Integer> {

	public List<Workshops> findByStatus(String status);
	
	public List<Workshops> findByNameContaining(String name);

	public List<Workshops> findByConferenceDetailId(int conferenceDetailsId);

	public List<Workshops> findByConferenceDetailTopic(String conferenceDetailsTopic);

	public List<Workshops> findByCreatedUser(String createdUser);

	public Optional<Workshops> findByName(String name);

	public Optional<Workshops> findByNameAndIdNotIn(String name, int id);

}
