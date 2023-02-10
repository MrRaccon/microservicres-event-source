package com.banking.cqrs.core.infraestructure;

import java.util.List;

import com.banking.cqrs.core.domain.BaseEntity;
import com.banking.cqrs.core.queries.BaseQuery;
import com.banking.cqrs.core.queries.QueryHandlerMethod;

public interface QueryDispatcher {
	
	<T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
	<U extends BaseEntity> List<U> send(BaseQuery query);

}
