package com.banking.account.cmd.api.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.handlers.EventSourcingHandler;

@Service
public class AccuntCommandHandler implements CommandHandler {

	@Autowired
	private EventSourcingHandler<AccountAggregate> eventSourcingHandler;
	
	@Override
	public void handle(OpenAccountCommand command) {
		var aggregate = new AccountAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(DepositFounsCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.depositFunds(command.getAmount());
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(WithDrawFundsCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		if(command.getAmount()>aggregate.getBalance()) {
			throw new IllegalStateException("Insuficientes fondos, no se puede retirar dinero");
		}
		aggregate.withDrawFunds(command.getAmount());
		eventSourcingHandler.save(aggregate);
		
	}

	@Override
	public void handle(CloseAccountCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.closeAccount();
		eventSourcingHandler.save(aggregate);
		
	}

}
