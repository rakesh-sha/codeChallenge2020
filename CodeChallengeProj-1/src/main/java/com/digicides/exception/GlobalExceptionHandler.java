package com.digicides.exception;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Class to Handles with Exception occured in apis.
 * 
 * @author rakesh
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler  {
	
	Logger logger=Logger.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * For Global Exception Handling 
	 * @param e Exception
	 * @param req WebRequest
	 * @return formatted Error Details as ErrorDetails obj
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception e, WebRequest req){
		ErrorDetails details=null;
		details=new ErrorDetails();
		details.setTimestamp(new Date());
		details.setMessage(e.getMessage());
		details.setDetails(req.getDescription(false));
		logger.error(e.getMessage(),e);
		return new ResponseEntity<>(details,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * For Validation related Response
	 * 
	 * @param exception
	 * @return
	 */
	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customeValidationHandler(MethodArgumentNotValidException exception){
		ErrorDetails details=null;
		details=new ErrorDetails();
		details.setTimestamp(new Date());
		details.setMessage("Validation Error");
		details.setDetails(exception.getBindingResult().getFieldError().getDefaultMessage());
		logger.error(exception.getMessage(),exception);
		return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
	}

}
