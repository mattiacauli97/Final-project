package it.epicode.energia.errors;

/**
 * classe per controllare gli errori
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
/**
 * Classe ErrorsControllerAdvice che gestisce gli errori
 * 
 */
@Slf4j
@ControllerAdvice
public class ErrorsControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity erroriInvalidazioni(MethodArgumentNotValidException ex) {
		log.info("erroriInvalidazioni");
		List<ObjectError> le = ex.getAllErrors();
		List<String>messaggiDiErrore = new ArrayList<>();
		for(ObjectError e : le) {
			messaggiDiErrore.add(e.getDefaultMessage());
		}
		return new ResponseEntity(messaggiDiErrore ,HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity erroriDiEsistenza(NoSuchElementException nsee) {
		
		return new ResponseEntity("si è verificato un errore" + nsee.getMessage(), HttpStatus.NOT_FOUND);
	}

	public ErrorsControllerAdvice() {
		log.info("ErrorsControllerAdvice");
	}
	
}
