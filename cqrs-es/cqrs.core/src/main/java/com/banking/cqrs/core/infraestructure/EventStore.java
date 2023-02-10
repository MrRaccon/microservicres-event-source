package com.banking.cqrs.core.infraestructure;

import java.util.List;

import com.banking.cqrs.core.events.BaseEvent;

public interface EventStore {
	
	void saveEvents(String aggregateId,Iterable<BaseEvent> baseEvents,int expectedVersion);
	List<BaseEvent> getEvent(String aggregateId);

}
