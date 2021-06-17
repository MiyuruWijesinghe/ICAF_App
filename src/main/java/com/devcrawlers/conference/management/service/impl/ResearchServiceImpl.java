package com.devcrawlers.conference.management.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devcrawlers.conference.management.service.ResearchService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ResearchServiceImpl implements ResearchService {

}
