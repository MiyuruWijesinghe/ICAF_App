package com.devcrawlers.conference.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.Roles;

@Repository
public interface RolesRepository extends MongoRepository<Roles, Integer> {

}
