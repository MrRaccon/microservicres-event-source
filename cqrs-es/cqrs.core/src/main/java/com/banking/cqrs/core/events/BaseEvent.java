package com.banking.cqrs.core.events;

import com.banking.cqrs.core.messages.MessageAbstract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * MESSAGES HEREDAN A LOS COMMANDS Y EVENTS
 * @author merkomunaalexis
 *
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent extends MessageAbstract {
	
	private int version;
	
}
