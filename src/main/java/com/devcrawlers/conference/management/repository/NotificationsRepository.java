package com.devcrawlers.conference.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devcrawlers.conference.management.model.Notifications;

@Repository
public interface NotificationsRepository extends MongoRepository<Notifications, Integer> {

	public List<Notifications> findByUserName(String userName);

	public List<Notifications> findByUserNameAndType(String userName, String type);

}
