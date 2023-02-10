package com.banking.account.query.api.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.domain.BankAccount;
import com.banking.account.query.dto.AccountLookupResponse;
import com.banking.account.query.dto.EqualityType;
import com.banking.cqrs.core.infraestructure.QueryDispatcher;

@RestController
@RequestMapping(path="api/v1/bankAccountLookup")
public class AccountLookupController {
	private static final Logger log = Logger.getLogger(AccountLookupController.class.getName());

	@Autowired
	private QueryDispatcher queryDispatcher;
	
	@GetMapping(path="/")
	public ResponseEntity<AccountLookupResponse> getaAllAccounts(){
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			var response = AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Se realizo con exito la consulta",null))
					.build();

			return new ResponseEntity<AccountLookupResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			var safeError = "Errores ejecutando la consulta";
			log.log(Level.SEVERE,safeError,e);
			return new ResponseEntity<>(new AccountLookupResponse(safeError),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	
	@GetMapping(path="/byId/{id}")
	public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value="id") String id){
	
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			var response = AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Se realizo con exito la consulta",null))
					.build();

			return new ResponseEntity<AccountLookupResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			var safeError = "Errores ejecutando la consulta";
			log.log(Level.SEVERE,safeError,e);
			return new ResponseEntity<>(new AccountLookupResponse(safeError),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping(path="/byHolder/{accountHolder}")
	public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value="accountHolder") String accountHolder){
	
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			var response = AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Se realizo con exito la consulta",null))
					.build();

			return new ResponseEntity<AccountLookupResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			var safeError = "Errores ejecutando la consulta";
			log.log(Level.SEVERE,safeError,e);
			return new ResponseEntity<>(new AccountLookupResponse(safeError),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	
	@GetMapping(path="/withBalance/{equalityType}/{balance}")
	public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable(value="equalityType") EqualityType equalityType,
			@PathVariable(value="balance") double balance){
	
		try {
			List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(balance,equalityType));
			if(accounts == null || accounts.isEmpty()) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			var response = AccountLookupResponse.builder()
					.accounts(accounts)
					.message(MessageFormat.format("Se realizo con exito la consulta",null))
					.build();

			return new ResponseEntity<AccountLookupResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			var safeError = "Errores ejecutando la consulta";
			log.log(Level.SEVERE,safeError,e);
			return new ResponseEntity<>(new AccountLookupResponse(safeError),HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
