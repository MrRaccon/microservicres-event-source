package com.banking.account.query.infrastructure.consumers;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithDrawEvent;

public interface EventConsumer {
	
    //representa que ya esta conocido
	void consumer(@Payload AccountOpenedEvent event,Acknowledgment ack);
	void consumer(@Payload FundsDepositedEvent event,Acknowledgment ack);
	void consumer(@Payload FundsWithDrawEvent event,Acknowledgment ack);
	void consumer(@Payload AccountClosedEvent event,Acknowledgment ack);


	
}
