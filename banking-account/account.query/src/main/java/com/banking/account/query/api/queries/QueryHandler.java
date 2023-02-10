package com.banking.account.query.api.queries;

import java.util.List;

import com.banking.cqrs.core.domain.BaseEntity;

public interface QueryHandler {
	
	List<BaseEntity> handler(FindAllAccountsQuery query);
	List<BaseEntity> handler(FindAccountByIdQuery query);
	List<BaseEntity> handler(FindAccountByHolderQuery query);
	List<BaseEntity> handler(FindAccountWithBalanceQuery query);

}
