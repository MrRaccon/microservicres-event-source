package com.banking.account.cmd.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;
import com.banking.cqrs.core.infraestructure.CommandDispatcherMediator;

@Service
public class AccountCommandDispatcher implements CommandDispatcherMediator {

	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

	@Override
	public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
		//AGREGA UN NUEVO VALOR 
		var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
		handlers.add(handler);
	}
 
	@Override
	public void send(BaseCommand command) {
		// TODO Auto-generated method stub
		//UN COMMAND SOLO PUEDE TENER UN HANDLER
			var handlers = routes.get(command.getClass());
			if(handlers ==null||handlers.isEmpty()) {
				throw new RuntimeException("El command handler no fue registrado");
			}
			if(handlers.size()>1) {
				throw new RuntimeException("No se puede enviar un command que tiene mas de un handler");
			}

			handlers.get(0).handle(command);
	}

}
