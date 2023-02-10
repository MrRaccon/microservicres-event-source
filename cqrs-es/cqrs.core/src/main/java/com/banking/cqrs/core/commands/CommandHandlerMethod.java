package com.banking.cqrs.core.commands;


/**
 * ESTO ES COLLEAGUE GENERAL
 * @author merkomunaalexis
 *
 * @param <T>
 */
@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand>{

	void handle(T command);
	
}
