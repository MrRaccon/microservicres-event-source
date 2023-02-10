package com.banking.account.cmd.api.controllers;


import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.common.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.dto.OpenAccountResponse;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infraestructure.CommandDispatcherMediator;

@RestController
@RequestMapping(path="/api/v1/openBankAccount")
public class AccountController {

	
	private static final Logger logger = Logger.getLogger(AccountController.class.getName());

	@Autowired
	private CommandDispatcherMediator commandDispatcherMediator;
	
	@PostMapping
	public ResponseEntity<BaseResponse > openAccount(@RequestBody OpenAccountCommand command){
		var id = Uuid.randomUuid().toString();
		command.setId(id);
		try {
			commandDispatcherMediator.send(command);
			return new ResponseEntity<>(new OpenAccountResponse("La cuenta del banco se creo exitosamente",id), HttpStatus.CREATED);
		} catch (IllegalStateException e) {
			logger.log(Level.WARNING,MessageFormat.format("No se puedo generar la cuenta de banco {0}", e.toString()));
			return new ResponseEntity<>(new OpenAccountResponse(e.toString()), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			var safeErrorMessage = MessageFormat.format("Errores mientras se procesaba el request {0}", id);
			logger.log(Level.SEVERE,safeErrorMessage,e);
			return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage,id), HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	
}
