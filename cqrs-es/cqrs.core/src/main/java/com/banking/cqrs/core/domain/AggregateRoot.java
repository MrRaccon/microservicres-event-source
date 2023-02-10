package com.banking.cqrs.core.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banking.cqrs.core.events.BaseEvent;

public abstract class AggregateRoot {

	protected String id;
	private int version=-1;
	
	private final List<BaseEvent> changes = new ArrayList<>();
	private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());
	
	public String getId() {
		return this.id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<BaseEvent> getUncommittedChanges() {
		return changes;
	}
	
    public void markChangesAsCommitted() {
    	this.changes.clear();
    }
	
    //se llama cuando se ejecuta un nuevo evento o cuando quiere reprocesar 
    protected void applyChange(BaseEvent event,Boolean isNewEvent) {
    	try {
			var method = getClass().getDeclaredMethod("apply", event.getClass());
			method.setAccessible(true);
			method.invoke(this, event);
		} catch (NoSuchMethodException e) {
			logger.log(Level.WARNING,MessageFormat.format("El metodo apply no fue encontrado para {0},", event.getClass().getName()));
		}catch(Exception e) {
			logger.log(Level.SEVERE,"ERRORES APLICANDO EL EVENTO AGGREGATE");
		}finally {
			if(isNewEvent) {
				changes.add(event);
			}
		}
    }
	

    /**
     * Agrega un nuevo evento
     * @param event
     */
    public void raiseEvent(BaseEvent event) {
    	applyChange(event, true);
    }
    
    /**
     * Reprocesa lista de eventos
     * @param events
     */
    public void replyEvents(Iterable<BaseEvent> events) {
    	events.forEach(event-> applyChange(event, false));
    }
}
