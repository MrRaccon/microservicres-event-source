package com.banking.account.cmd.infrastructure;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.exceptions.ConcurrencyException;
import com.banking.cqrs.core.infraestructure.EventStore;

@Service
public class AccountEventStore implements EventStore {

	@Autowired
	private EventStoreRepository eventStoreRepository;
	
	@Override
	public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
      //retorna eventos existentes
		var eventStream = eventStoreRepository.findByaggregateIdentifier(aggregateId);
		if(expectedVersion != 1 && eventStream.get(eventStream.size()-1).getVersion()!=expectedVersion) {
			throw new ConcurrencyException();
		}
		var version = expectedVersion;
		for(var event :events) {
			version++;
			event.setVersion(version);
			var eventModel = EventModel.builder()
					.aggregateIdentifier(aggregateId)
					.timeStamp(new Date())
					.aggregateType(AccountAggregate.class.getTypeName())
					.version(version)
					.eventType(event.getClass().getTypeName())
					.eventData(event)
					.build();
			var persistenceEvent = eventStoreRepository.save(eventModel);
			
			if(persistenceEvent != null) {
					//se debe de crear el evento para KAFKA
			}
		}
		
	}

	@Override
	public List<BaseEvent> getEvent(String aggregateId) {
		var eventStream = eventStoreRepository.findByaggregateIdentifier(aggregateId);
		if(eventStream==null||eventStream.isEmpty()) {
			throw new AggregateNotFoundException("La cuenta del banco es incorrecta");
		}
		
		return eventStream.stream()
				.map(x->x.getEventData())
				.collect(Collectors.toList());
	}
	
	

}
