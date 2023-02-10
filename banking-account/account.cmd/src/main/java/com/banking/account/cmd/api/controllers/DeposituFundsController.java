package com.banking.account.cmd.api.controllers;


import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.DepositFounsCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.infraestructure.CommandDispatcherMediator;

@RestController
@RequestMapping(path="/api/v1/depositFunds")
public class DeposituFundsController {
	
	
	private static final Logger log = Logger.getLogger(DeposituFundsController.class.getName());
	
	@Autowired
	private CommandDispatcherMediator commandDispatcherMediator;
	
	@PutMapping(path="/{id}")
	public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value="id") String id,
			@RequestBody DepositFounsCommand command ){
		 
		try {
			command.setId(id);
			commandDispatcherMediator.send(command);
			return new ResponseEntity<>(new BaseResponse("Se deposito correctamente"), HttpStatus.OK);

		} catch (IllegalStateException | AggregateNotFoundException e) {
			log.log(Level.WARNING,MessageFormat.format("El cliente envio un request mal {0}", e.toString()));
			return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			var safeErrorMessage = MessageFormat.format("Errores mientras se procesaba el request {0}", id);
			log.log(Level.SEVERE,safeErrorMessage,e);
			return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	


}
