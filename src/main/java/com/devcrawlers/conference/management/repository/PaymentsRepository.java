package com.devcrawlers.conference.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devcrawlers.conference.management.model.Payments;

@Repository
public interface PaymentsRepository extends MongoRepository<Payments, Integer> {

}
