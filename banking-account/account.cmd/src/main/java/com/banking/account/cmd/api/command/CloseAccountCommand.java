package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand{

	public CloseAccountCommand (String id) {
		super(id);
	}
}
