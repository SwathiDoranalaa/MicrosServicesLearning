package com.ricomincia.account.service;

import com.ricomincia.account.dto.CustomerDto;

public interface IAccountsService {

  void createAccount(CustomerDto customerDto);
  CustomerDto fetchAccount(Long mobileNumber);

  boolean updateAccount(CustomerDto customerDto);

  boolean deleteAccount(Long mobileNumber);
}
