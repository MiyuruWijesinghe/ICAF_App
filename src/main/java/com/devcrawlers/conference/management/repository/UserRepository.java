package com.devcrawlers.conference.management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.devcrawlers.conference.management.model.User;

/**
 * User Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Author    IT No.        Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-05-2021   MiyuruW   IT19020990     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

	public Optional<User> findByUserName(String userName);

}
