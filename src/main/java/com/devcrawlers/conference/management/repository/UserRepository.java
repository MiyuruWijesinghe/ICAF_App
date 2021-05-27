package com.devcrawlers.conference.management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

	public Optional<User> findByUserName(String userName);

}
