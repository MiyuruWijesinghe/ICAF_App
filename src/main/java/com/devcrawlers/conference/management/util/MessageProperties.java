package com.devcrawlers.conference.management.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class MessageProperties {

	protected static final Properties properties = new Properties();
	
	static {
		try {
			InputStream input = new FileInputStream(CommonConstants.NOTIFICATION_PROPERTY_FILE);
	        properties.load(input);
	    } catch (IOException ex) {
	        
	    }
	}
	
	protected static final String RECORD_CREATED = properties.getProperty("common.saved");
	protected static final String RECORD_UPDATED = properties.getProperty("common.updated");
	protected static final String RECORD_NOT_FOUND = properties.getProperty("common.record-not-found");
	protected static final String COMMON_INTERNAL_SERVER_ERROR = properties.getProperty("common.internal-server-error");
	protected static final String COMMON_ERROR = properties.getProperty("common.error");
	protected static final String COMMON_DUPLICATE = properties.getProperty("common.duplicate");
	protected static final String COMMON_STATUS_PATTERN = properties.getProperty("common.status-pattern");
	protected static final String COMMON_STATUS_NOT_NULL = properties.getProperty("common.status-not-null");
	protected static final String COMMON_CREATEDUSER_NOT_NULL = properties.getProperty("common.createduser-not-null");
	protected static final String COMMON_MODIFIEDUSER_NOT_NULL = properties.getProperty("common.modifieduser-not-null");
	protected static final String COMMON_NOT_NULL = properties.getProperty("common.not-null");
	protected static final String COMMON_NUMERIC_PATTERN = properties.getProperty("common.numeric-pattern");
	protected static final String COMMON_INVALID_VALUE = properties.getProperty("common.invalid-value");
	protected static final String BAD_REQUEST = properties.getProperty("common.bad-request");
	protected static final String SERVICE_NOT_AVAILABLE = properties.getProperty("common-service.not-available");
	protected static final String CHILD_RECORD = properties.getProperty("common-child-record.available");
	protected static final String CHILD_RECORD_STATUS = properties.getProperty("common-child-record-status.available");
	protected static final String PAGEABLE_LENGTH = properties.getProperty("common.pageable-length");
	protected static final String NO_RECORD_TO_SAVED = properties.getProperty("common.no-record");
	protected static final String COMMON_DELETED = properties.getProperty("common.deleted");
	
}
