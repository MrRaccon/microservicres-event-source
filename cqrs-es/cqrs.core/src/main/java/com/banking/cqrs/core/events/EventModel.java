package com.banking.cqrs.core.events;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection ="eventStore") //señaliza el model a guardar en la base de datos de mongo
public class EventModel {

	@Id
	private String id;
	
	private Date timeStamp;
	
	private String aggregateIdentifier;
	
	private String aggregateType;
	
	private int version;
	
	private String eventType;
	
	private BaseEvent eventData;
}
