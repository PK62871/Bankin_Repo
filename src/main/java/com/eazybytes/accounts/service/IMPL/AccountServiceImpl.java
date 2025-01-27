package com.eazybytes.accounts.service.IMPL;

import com.eazybytes.accounts.Dto.AccountDto;
import com.eazybytes.accounts.Dto.CustomerDto;
import com.eazybytes.accounts.constant.AccountConstant;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepo;
import com.eazybytes.accounts.repository.CustomerRepo;
import com.eazybytes.accounts.service.IAccountService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {


    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepo.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("Customer is Already Present by this Mobile Number : "
                    + customerDto.getMobileNumber());

        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Prabhakar");
        Customer savedCustomer = customerRepo.save(customer);

        accountRepo.save(createNewAccount(savedCustomer));

    }


    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepo.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer","MobileNumber",mobileNumber));

       Accounts account =  accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.mapToAccountDto(account,new AccountDto()));

        return customerDto;
    }

    /**
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountDto accountDto = customerDto.getAccountDto();

        if(accountDto != null){
            Accounts accounts = accountRepo.findById(accountDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","AccountNumber",accountDto.getAccountNumber().toString())
            );

            Accounts accounts1 = AccountsMapper.mapToAccounts(accountDto, new Accounts());
            accountRepo.save(accounts1);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
            );

            Customer customer1 = CustomerMapper.mapToCustomer(customerDto, new Customer());
            customerRepo.save(customer1);
            isUpdated= true;
        }
        return isUpdated;
    }


    private Accounts createNewAccount(Customer customer){
        Accounts newAccounts = new Accounts();

        newAccounts.setCustomerId(customer.getCustomerId());
        long randomNumber = 1000000000L + new Random().nextInt();

        newAccounts.setAccountNumber(randomNumber);
        newAccounts.setAccountType(AccountConstant.SAVINGS);
        newAccounts.setBranchAddress(AccountConstant.ADDRESS);
        newAccounts.setCreatedBy("Prabhakar");
        newAccounts.setCreatedBy("Prabhakar");
        newAccounts.setCreatedAt(LocalDateTime.now());
        return  newAccounts;
    }
}
