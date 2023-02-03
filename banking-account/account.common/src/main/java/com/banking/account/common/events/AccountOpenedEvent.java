package com.banking.account.common.events;

import java.util.Date;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Despues de ejecutar el command de abrir cuenta se dispara este evento
 * @author merkomunaalexis
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent{

	private String accountHolder;
	
	private AccountType accountType;
	
	private Date createDate;
	
	private double openingBalance;
}
