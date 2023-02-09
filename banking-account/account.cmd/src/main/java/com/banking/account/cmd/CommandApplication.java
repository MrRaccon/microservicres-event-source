package com.banking.account.cmd;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.banking.account.cmd.api.command.CloseAccountCommand;
import com.banking.account.cmd.api.command.CommandHandler;
import com.banking.account.cmd.api.command.DepositFounsCommand;
import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.command.WithDrawFundsCommand;
import com.banking.cqrs.core.infraestructure.CommandDispatcherMediator;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcherMediator commandDispatcherMediator;
	
	@Autowired
	private CommandHandler commandHandler;
	
	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}
	
	@PostConstruct
	public void registerHandlers() {
		commandDispatcherMediator.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispatcherMediator.registerHandler(DepositFounsCommand.class, commandHandler::handle);
		commandDispatcherMediator.registerHandler(WithDrawFundsCommand.class, commandHandler::handle);
		commandDispatcherMediator.registerHandler(CloseAccountCommand.class, commandHandler::handle);
		
	}

}
