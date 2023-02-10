package com.banking.account.cmd.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.cqrs.core.events.EventModel;

public interface EventStoreRepository extends MongoRepository<EventModel, String>{

	 List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
