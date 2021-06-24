package com.devcrawlers.conference.management.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.devcrawlers.conference.management.model.Research;
import com.devcrawlers.conference.management.repository.ConferenceDetailsRepository;
import com.devcrawlers.conference.management.repository.ResearchRepository;
import com.devcrawlers.conference.management.resource.CommonApproveRejectResource;
import com.devcrawlers.conference.management.resource.ResearchAddResource;
import com.devcrawlers.conference.management.resource.ResearchUpdateResource;
import com.devcrawlers.conference.management.service.ResearchService;
import com.devcrawlers.conference.management.util.IdGenerator;

@Component
@Transactional(rollbackFor=Exception.class)
public class ResearchServiceImpl implements ResearchService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ResearchRepository researchRepository;
	
	@Autowired
	private ConferenceDetailsRepository conferenceDetailsRepository;
	
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	private int generateId() {
		List<Research> researchList = researchRepository.findAll();
		List<Integer> researchIdList = new ArrayList<>();
		
		for(Research researchObject : researchList) {
			researchIdList.add(researchObject.getId());
		}
		
		return IdGenerator.generateIDs(researchIdList);	
	}
	
	@Override
	public List<Research> findAll() {
		return researchRepository.findAll();
	}

	@Override
	public Optional<Research> findById(int id) {
		Optional<Research> research = researchRepository.findById(id);
		if (research.isPresent()) {
			return Optional.ofNullable(research.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Research> findByStatus(String status) {
		return researchRepository.findByStatus(status);
	}

	@Override
	public List<Research> findByName(String name) {
		return researchRepository.findByNameContaining(name);
	}

	@Override
	public List<Research> findByConferenceDetailsId(int conferenceDetailsId) {
		return researchRepository.findByConferenceDetailId(conferenceDetailsId);
	}

	@Override
	public List<Research> findByConferenceDetailsTopic(String conferenceDetailsTopic) {
		return researchRepository.findByConferenceDetailTopic(conferenceDetailsTopic);
	}

	@Override
	public List<Research> findByCreatedUser(String createdUser) {
		return researchRepository.findByCreatedUser(createdUser);
	}

	@Override
	public Integer saveResearch(ResearchAddResource researchAddResource) {
		
		Research research = new Research();
		
		Optional<Research> isPresentResearch = researchRepository.findByName(researchAddResource.getName());
        if (isPresentResearch.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("research.duplicate"), "message");
		}
        
        Optional<ConferenceDetails> conferenceDetails = conferenceDetailsRepository.findByIdAndStatus(Integer.parseInt(researchAddResource.getConferenceDetailsId()), CommonStatus.APPROVED.toString());
		if (!conferenceDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("conference-details.invalid-value"), "message");
		} else {
			research.setConferenceDetail(conferenceDetails.get());
		}
		
		research.setId(generateId());
		research.setName(researchAddResource.getName());
		research.setDescription(researchAddResource.getDescription());
		research.setPublishedDate(formatDate(researchAddResource.getPublishedDate()));
		research.setDocumentURL(researchAddResource.getDocumentURL());
		research.setStatus(CommonStatus.PENDING.toString());
		research.setCreatedUser("MKW");
		research.setCreatedDate(new Date());
		researchRepository.save(research);
		return research.getId();
	}

	@Override
	public Research updateResearch(int id, ResearchUpdateResource researchUpdateResource) {
		
		Optional<Research> isPresentResearch = researchRepository.findById(id);
		if (!isPresentResearch.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Optional<Research> isPresentResearchByName = researchRepository.findByNameAndIdNotIn(researchUpdateResource.getName(), id);
		if (isPresentResearchByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("research.duplicate"), "message");
		
		Research research = isPresentResearch.get();
		research.setName(researchUpdateResource.getName());
		research.setDescription(researchUpdateResource.getDescription());
		research.setPublishedDate(formatDate(researchUpdateResource.getPublishedDate()));
		research.setDocumentURL(researchUpdateResource.getDocumentURL());
		research.setStatus(CommonStatus.PENDING.toString());
		research.setCreatedUser("MKW");
		research.setCreatedDate(new Date());
		research.setRemarks(null);
		research.setApprovedUser(null);
		research.setApprovedDate(null);
		research.setRejectedUser(null);
		research.setRejectedDate(null);
		researchRepository.save(research);
		return research;
	}

	@Override
	public String deleteResearch(int id) {
		
		Optional<Research> isPresentResearch = researchRepository.findById(id);
		if (!isPresentResearch.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		researchRepository.deleteById(id);
		return environment.getProperty("common.deleted");
	}

	@Override
	public String approveResearch(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<Research> isPresentResearch = researchRepository.findById(id);
		if (!isPresentResearch.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Research research = isPresentResearch.get();
		research.setStatus(CommonStatus.APPROVED.toString());
		research.setRemarks(commonApproveRejectResource.getRemarks());
		research.setApprovedUser(commonApproveRejectResource.getUserName());
		research.setApprovedDate(new Date());
		research.setRejectedUser(null);
		research.setRejectedDate(null);
		researchRepository.save(research);
		
		return environment.getProperty("common.approved");
	}

	@Override
	public String rejectResearch(int id, CommonApproveRejectResource commonApproveRejectResource) {
		
		Optional<Research> isPresentResearch = researchRepository.findById(id);
		if (!isPresentResearch.isPresent()) {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
		
		Research research = isPresentResearch.get();
		research.setStatus(CommonStatus.REJECTED.toString());
		research.setRemarks(commonApproveRejectResource.getRemarks());
		research.setRejectedUser(commonApproveRejectResource.getUserName());
		research.setRejectedDate(new Date());
		research.setApprovedUser(null);
		research.setApprovedDate(null);
		researchRepository.save(research);
		
		return environment.getProperty("common.rejected");
	}

}
