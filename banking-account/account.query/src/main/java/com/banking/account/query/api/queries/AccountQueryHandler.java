package com.banking.account.query.api.queries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import com.banking.account.query.dto.EqualityType;
import com.banking.cqrs.core.domain.BaseEntity;

@Service
public class AccountQueryHandler implements QueryHandler{

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public List<BaseEntity> handler(FindAllAccountsQuery query) {
		Iterable<BankAccount> bankAccounts = accountRepository.findAll();
		List<BaseEntity> bankAccountList = new ArrayList<>();
		bankAccounts.forEach(bankAccountList::add);
		return bankAccountList;
	}

	@Override
	public List<BaseEntity> handler(FindAccountByIdQuery query) {
		var bankAccount  = accountRepository.findById(query.getId());
		if(bankAccount.isEmpty()) {
			return null;
		}
		
		List<BaseEntity> bankAccountList = new ArrayList<>();
		bankAccountList.add(bankAccount.get());
		return bankAccountList;
	}

	@Override
	public List<BaseEntity> handler(FindAccountByHolderQuery query) {
		var bankAccount  = accountRepository.findByAccountHolder(query.getAccountHolder());
		if(bankAccount.isEmpty()) {
			return null;
		}
		
		List<BaseEntity> bankAccountList = new ArrayList<>();
		bankAccountList.add(bankAccount.get());
		return bankAccountList;
		}

	@Override
	public List<BaseEntity> handler(FindAccountWithBalanceQuery query) {
		List<BaseEntity> bankAccountList = query.getEqualityType() ==EqualityType.GREATER_THAN? accountRepository.findByBalanceGreaterThan(query.getBalance())
				:accountRepository.findByBalanceLessThan(query.getBalance());

		return bankAccountList;
	}

	
}
