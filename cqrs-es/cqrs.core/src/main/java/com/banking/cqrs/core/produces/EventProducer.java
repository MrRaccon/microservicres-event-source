package com.banking.cqrs.core.produces;

import com.banking.cqrs.core.events.BaseEvent;

public interface EventProducer {
	
	void produce(String topic,BaseEvent event);
	
	

}
