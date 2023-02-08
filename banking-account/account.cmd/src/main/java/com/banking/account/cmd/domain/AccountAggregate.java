package com.banking.account.cmd.domain;

import java.util.Date;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithDrawEvent;
import com.banking.cqrs.core.domain.AggregateRoot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot{

	private Boolean active;
	
	private double balance;

	public AccountAggregate(OpenAccountCommand command) {
		raiseEvent(AccountOpenedEvent.builder()
				.id(command.getId())
				.accountHolder(command.getAccountHolder())
				.createDate(new Date())
				.accountType(command.getAccountType())
				.openingBalance(command.getOpeningBalance())
				.build());
	}

	public void apply(AccountOpenedEvent event) {
		this.id =event.getId();
		this.active =true;
		this.balance = event.getOpeningBalance();
	}
	
   public void depositFunds(double amount) {
	   if(!this.active) {
		   throw new IllegalStateException("LOS FONDOS NO PUEDE SER DEPOSITADOS EN ESTA CUENTA");
	   }
	   if(amount<=0){
		   throw new IllegalStateException("El deposito no puede ser cero o menos que cero	");
		   
	   }
	   raiseEvent(FundsDepositedEvent.builder()
			   .id(this.id)
			   .amount(amount)
			   .build());
   }
   
   public void apply(FundsDepositedEvent event) {
	   this.id = event.getId();
	   this.balance += event.getAmount();
   }
   
   //metodo para retirar fondos
   public void withDrawFunds(double amount) {
	   if(!this.active) {
		   throw new IllegalStateException("La cuenta esta cerrada");
	   }
	   raiseEvent(FundsWithDrawEvent.builder().
			   id(this.id)
			   .amount(amount)
			   .build());
	   
   }
   // apply de retirar fondos
   public void apply(FundsWithDrawEvent event) {
	   this.id =event.getId();
	   this.balance -= event.getAmount();
   }
   
   
   
   public void closeAccount() {
	   if(!this.active) {
		   throw new IllegalStateException("La cuenta esta cerrada");
	   }
	   raiseEvent(AccountClosedEvent.builder()
			   .id(this.id)
			   .build());
   }

   public void apply (AccountClosedEvent event) {
	    this.id =event.getId();
	    this.active =false;
   }
}
