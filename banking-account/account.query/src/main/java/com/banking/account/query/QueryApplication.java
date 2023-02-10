package com.banking.account.query;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.api.queries.QueryHandler;
import com.banking.cqrs.core.infraestructure.QueryDispatcher;

@SpringBootApplication
public class QueryApplication {
	
	@Autowired
	private QueryDispatcher queryDispatcher;

	@Autowired
	private QueryHandler queryHandler;
	
	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}
	
	@PostConstruct
	private void registerHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handler);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handler);
		queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handler);
		queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handler);

	}

}
