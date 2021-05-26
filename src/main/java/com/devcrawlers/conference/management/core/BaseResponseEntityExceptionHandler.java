package com.devcrawlers.conference.management.core;

import java.lang.reflect.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.devcrawlers.conference.management.exception.CodeUniqueException;
import com.devcrawlers.conference.management.exception.InvalidServiceIdException;
import com.devcrawlers.conference.management.exception.NoRecordFoundException;
import com.devcrawlers.conference.management.exception.UserNotFoundException;
import com.devcrawlers.conference.management.exception.ValidateRecordException;
import com.devcrawlers.conference.management.resource.RolesAddResource;
import com.devcrawlers.conference.management.resource.RolesUpdateResource;
import com.devcrawlers.conference.management.resource.SuccessAndErrorDetailsResource;
import com.devcrawlers.conference.management.resource.UserAddResource;
import com.devcrawlers.conference.management.resource.UserUpdateResource;
import com.devcrawlers.conference.management.resource.ValidateResource;


@RestControllerAdvice
public class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Environment environment;
	
	
	@Override 
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) { 
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.invalid-url-pattern"));
		successAndErrorDetailsDataObject.setDetails(ex.getMessage());
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.argument-type-mismatch"));
		successAndErrorDetailsDataObject.setDetails(ex.getMessage());
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<Object> userNotFoundException(UserNotFoundException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsResource.setMessages(environment.getProperty("common.user-not-found"));
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({CodeUniqueException.class})
	public ResponseEntity<Object> codeUniqueException(CodeUniqueException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.code-duplicate"));
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({NoRecordFoundException.class})
	public ResponseEntity<Object> noRecordFoundException(NoRecordFoundException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.record-not-found"));
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		try {
			Field sField = null;
			String fieldName = null;
			Integer index = null;
			Integer index1 = null;
			Integer atPoint = null;
			String className = ex.getBindingResult().getObjectName();
			
			switch (className) {

			case "userAddResource":
				UserAddResource userAddResource = new UserAddResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = userAddResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(userAddResource.getClass().cast(userAddResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(userAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "userUpdateResource":
				UserUpdateResource userUpdateResource = new UserUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = userUpdateResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(userUpdateResource.getClass().cast(userUpdateResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(userUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "rolesAddResource":
				RolesAddResource rolesAddResource = new RolesAddResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = rolesAddResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(rolesAddResource.getClass().cast(rolesAddResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(rolesAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "rolesUpdateResource":
				RolesUpdateResource rolesUpdateResource = new RolesUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = rolesUpdateResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(rolesUpdateResource.getClass().cast(rolesUpdateResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(rolesUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);	

			default:
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (Exception e) {
			SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.internal-server-error"));
			successAndErrorDetailsResource.setDetails(e.getMessage());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler({InvalidServiceIdException.class})
	public ResponseEntity<Object> invalidServiceIdException(InvalidServiceIdException ex, WebRequest request) {
		ValidateResource validateResource=new ValidateResource();
		switch(ex.getServiceEntity()) 
        { 
            case USER_ID:
            	validateResource.setUserId(ex.getMessage());
            	break;
            default: 
 
        } 
        return new ResponseEntity<>(validateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

	@ExceptionHandler({ValidateRecordException.class})
	public ResponseEntity<Object> validateRecordException(ValidateRecordException ex, WebRequest request) {
		try {
			ValidateResource typeValidation = new ValidateResource();		
			Class validationDetailClass = typeValidation.getClass();
			Field sField = validationDetailClass.getDeclaredField(ex.getField());
			sField.setAccessible(true);
			sField.set(typeValidation, ex.getMessage());		
			return new ResponseEntity<>(typeValidation, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			SuccessAndErrorDetailsResource successAndErrorDetailsDto = new SuccessAndErrorDetailsResource();
			successAndErrorDetailsDto.setMessages(environment.getProperty("common.internal-server-error"));
			successAndErrorDetailsDto.setDetails(e.getMessage());
			return new ResponseEntity<>(successAndErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
