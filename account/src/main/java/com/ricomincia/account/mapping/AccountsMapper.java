package com.ricomincia.account.mapping;

import com.ricomincia.account.dto.AccountsDto;
import com.ricomincia.account.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts account, AccountsDto accountDto){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Accounts mapToAccount(AccountsDto accountDto, Accounts account){
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }
}
