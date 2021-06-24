package com.devcrawlers.conference.management.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.devcrawlers.conference.management.model.Workshops;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.WorkshopsAddResource;
import com.devcrawlers.conference.management.resource.WorkshopsUpdateResource;

@Service
public interface WorkshopsService {

	public List<Workshops> findAll();
	
	public Optional<Workshops> findById(int id);
	
	public List<Workshops> findByStatus(String status);
	
	public List<Workshops> findByName(String name);
	
	public List<Workshops> findByConferenceDetailsId(int conferenceDetailsId);
	
	public List<Workshops> findByConferenceDetailsTopic(String conferenceDetailsTopic);
	
	public List<Workshops> findByCreatedUser(String createdUser);
	
	public Integer saveWorkshop(WorkshopsAddResource workshopsAddResource);
	
	public Workshops updateWorkshop(int id, WorkshopsUpdateResource workshopsUpdateResource);
	
	public String deleteWorkshop(int id);
	
	public String approveWorkshop(int id, CommonApproveRejectResource commonApproveRejectResource);

	public String rejectWorkshop(int id, CommonApproveRejectResource commonApproveRejectResource);
}
