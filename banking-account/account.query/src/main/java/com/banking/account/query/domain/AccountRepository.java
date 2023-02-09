package com.banking.account.query.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.banking.cqrs.core.domain.BaseEntity;

public interface AccountRepository extends CrudRepository<BankAccount, String> {

	Optional<BankAccount> findByAccountHolder(String accountHolder);

	List<BaseEntity> findByBalanceGreaterThan(double balance);

	List<BaseEntity> findByBalanceLessThan(double balance);
}
