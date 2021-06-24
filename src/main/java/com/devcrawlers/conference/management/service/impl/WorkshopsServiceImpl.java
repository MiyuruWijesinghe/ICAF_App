package com.devcrawlers.conference.management.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.devcrawlers.conference.management.enums.CommonStatus;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.exception.ValidateRecordException;
import com.devcrawlers.conference.management.model.ConferenceDetails;
import com.devcrawlers.conference.management.model.Workshops;
import com.devcrawlers.conference.management.repository.ConferenceDetailsRepository;
import com.devcrawlers.conference.management.repository.WorkshopsRepository;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.WorkshopsAddResource;
import com.devcrawlers.conference.management.resource.WorkshopsUpdateResource;
import com.devcrawlers.conference.management.service.WorkshopsService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class WorkshopsServiceImpl implements WorkshopsService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private WorkshopsRepository workshopsRepository;
	
	@Autowired
	private ConferenceDetailsRepository conferenceDetailsRepository;
	
	
	private int generateId() {
		List<Workshops> workshopsList = workshopsRepository.findAll();
		List<Integer> workshopsIdList = new ArrayList<>();
		
		for(Workshops workshopsObject : workshopsList) {
			workshopsIdList.add(workshopsObject.getId());
		}
		
		return IdGenerator.generateIDs(workshopsIdList);	
	}
	
	@Override
	public List<Workshops> findAll() {
		return workshopsRepository.findAll();
	}

	@Override
	public Optional<Workshops> findById(int id) {
		Optional<Workshops> workshops = workshopsRepository.findById(id);
		if (workshops.isPresent()) {
			return Optional.ofNullable(workshops.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Workshops> findByStatus(String status) {
		return workshopsRepository.findByStatus(status);
	}

	@Override
	public List<Workshops> findByName(String name) {
		return workshopsRepository.findByNameContaining(name);
	}
	
	@Override
	public List<Workshops> findByConferenceDetailsId(int conferenceDetailsId) {
		return workshopsRepository.findByConferenceDetailId(conferenceDetailsId);
	}

	@Override
	public List<Workshops> findByConferenceDetailsTopic(String conferenceDetailsTopic) {
		return workshopsRepository.findByConferenceDetailTopic(conferenceDetailsTopic);
	}

	@Override
	public List<Workshops> findByCreatedUser(String createdUser) {
		return workshopsRepository.findByCreatedUser(createdUser);
	}

	@Override
	public Integer saveWorkshop(WorkshopsAddResource workshopsAddResource) {
		
		Workshops workshops = new Workshops();
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findByName(workshopsAddResource.getName());
        if (isPresentWorkshop.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("workshop.duplicate"), "message");
		}
        
        Optional<ConferenceDetails> conferenceDetails = conferenceDetailsRepository.findByIdAndStatus(Integer.parseInt(workshopsAddResource.getConferenceDetailsId()), CommonStatus.APPROVED.toString());
		if (!conferenceDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("conference-details.invalid-value"), "message");
		} else {
			workshops.setConferenceDetail(conferenceDetails.get());
		}
		
		workshops.setId(generateId());
		workshops.setName(workshopsAddResource.getName());
		workshops.setDescription(workshopsAddResource.getDescription());
		workshops.setDocumentURL(workshopsAddResource.getDocumentURL());
		workshops.setStatus(CommonStatus.PENDING.toString());
		workshops.setCreatedUser("MKW");
		workshops.setCreatedDate(new Date());
		workshopsRepository.save(workshops);
		return workshops.getId();
	}

	@Override
	public Workshops updateWorkshop(int id, WorkshopsUpdateResource workshopsUpdateResource) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Workshops> isPresentWorkshopByName = workshopsRepository.findByNameAndIdNotIn(workshopsUpdateResource.getName(), id);
		if (isPresentWorkshopByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("workshop.duplicate"), "message");
		
		Workshops workshops = isPresentWorkshop.get();
		workshops.setName(workshopsUpdateResource.getName());
		workshops.setDescription(workshopsUpdateResource.getDescription());
		workshops.setDocumentURL(workshopsUpdateResource.getDocumentURL());
		workshops.setStatus(CommonStatus.PENDING.toString());
		workshops.setCreatedUser("MKW");
		workshops.setCreatedDate(new Date());
		workshops.setRemarks(null);
		workshops.setApprovedUser(null);
		workshops.setApprovedDate(null);
		workshops.setRejectedUser(null);
		workshops.setRejectedDate(null);
		workshopsRepository.save(workshops);
		return workshops;
	}

	@Override
	public String deleteWorkshop(int id) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		workshopsRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

	@Override
	public String approveWorkshop(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Workshops workshops = isPresentWorkshop.get();
		workshops.setStatus(CommonStatus.APPROVED.toString());
		workshops.setRemarks(commonApproveRejectResource.getRemarks());
		workshops.setApprovedUser(commonApproveRejectResource.getUserName());
		workshops.setApprovedDate(new Date());
		workshops.setRejectedUser(null);
		workshops.setRejectedDate(null);
		workshopsRepository.save(workshops);
		
		return environment.getProperty("common.approved");
	}

	@Override
	public String rejectWorkshop(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<Workshops> isPresentWorkshop = workshopsRepository.findById(id);
		if (!isPresentWorkshop.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Workshops workshops = isPresentWorkshop.get();
		workshops.setStatus(CommonStatus.REJECTED.toString());
		workshops.setRemarks(commonApproveRejectResource.getRemarks());
		workshops.setRejectedUser(commonApproveRejectResource.getUserName());
		workshops.setRejectedDate(new Date());
		workshops.setApprovedUser(null);
		workshops.setApprovedDate(null);
		workshopsRepository.save(workshops);
		
		return environment.getProperty("common.rejected");
	}

}
