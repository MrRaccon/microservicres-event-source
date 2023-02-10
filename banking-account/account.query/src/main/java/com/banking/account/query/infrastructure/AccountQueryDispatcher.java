package com.banking.account.query.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.banking.cqrs.core.domain.BaseEntity;
import com.banking.cqrs.core.infraestructure.QueryDispatcher;
import com.banking.cqrs.core.queries.BaseQuery;
import com.banking.cqrs.core.queries.QueryHandlerMethod;

@Service
public class AccountQueryDispatcher implements QueryDispatcher{
	
	private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes =new HashMap<>(); 	

	@Override
	public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
     var handlers = routes.computeIfAbsent(type,c-> new LinkedList<>());
     handlers.add(handler);
		
	}

	@Override
	public <U extends BaseEntity> List<U> send(BaseQuery query) {
      var handlers = routes.get(query.getClass());
      if(handlers ==null || handlers.isEmpty()) {
    	  throw new RuntimeException("ningun query handler fue registrado para este objeto query");
      }
      if(handlers.size()>1) {
    	  throw new RuntimeException("No puede enviar un query que tenga dos o mas handlers");
      }
		return handlers.get(0).handle(query);
	}

}
