package com.banking.account.cmd.api.command;

import com.banking.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class WithDrawFundsCommand extends BaseCommand{
 
	private double amount;
}
