package com.banking.cqrs.core.infraestructure;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;

/**
 * funciona como el mediator
 * 
 * @author merkomunaalexis
 *
 */
public interface CommandDispatcherMediator {
	
	<T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
      void send(BaseCommand command);
}
