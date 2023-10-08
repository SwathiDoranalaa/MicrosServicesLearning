package com.ricomincia.account.service.impl;

import com.ricomincia.account.constants.AccountsConstants;
import com.ricomincia.account.dto.AccountsDto;
import com.ricomincia.account.dto.CustomerDto;
import com.ricomincia.account.entity.Accounts;
import com.ricomincia.account.entity.Customer;
import com.ricomincia.account.exception.CustomerAlreadyExistsException;
import com.ricomincia.account.exception.ResourceNotFoundException;
import com.ricomincia.account.mapping.AccountsMapper;
import com.ricomincia.account.mapping.CustomerMapper;
import com.ricomincia.account.repository.AccountsRepository;
import com.ricomincia.account.repository.CustomerRepository;
import com.ricomincia.account.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchAccount(Long mobileNumber) {
        Customer customer =customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber.toString())
        );
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto =customerDto.getAccountsDto();
        if(accountsDto !=null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber",accountsDto.getAccountNumber().toString())
            );
           AccountsMapper.mapToAccount(accountsDto,accounts);
           accounts = accountsRepository.save(accounts);

           Long customerId = accounts.getCustomerId();
           Customer customer = customerRepository.findById(customerId).orElseThrow(
                   () -> new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
           );
           CustomerMapper.mapToCustomer(customerDto,customer);
           customerRepository.save(customer);
           isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(Long mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber.toString())
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer savedCustomer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(savedCustomer.getCustomerId());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
