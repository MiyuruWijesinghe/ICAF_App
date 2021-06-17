package com.devcrawlers.conference.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devcrawlers.conference.management.model.Workshops;

@Repository
public interface WorkshopsRepository extends MongoRepository<Workshops, Integer> {

}
