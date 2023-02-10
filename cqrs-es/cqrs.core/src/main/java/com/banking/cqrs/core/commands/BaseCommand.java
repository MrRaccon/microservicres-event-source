package com.banking.cqrs.core.commands;

import com.banking.cqrs.core.messages.MessageAbstract;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseCommand extends MessageAbstract{

	protected BaseCommand(String id) {
		super(id);
	}
	
}
