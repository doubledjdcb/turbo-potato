package org.caringbridge.services.security.repository;

import java.util.List;

import org.caringbridge.services.security.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
	   
    public List<Profile> findByEmailAddress(String emailAddress);  
}
