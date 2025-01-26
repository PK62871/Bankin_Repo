package com.eazybytes.accounts.service.IMPL;

import com.eazybytes.accounts.Dto.CustomerDto;
import com.eazybytes.accounts.constant.AccountConstant;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistException;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepo;
import com.eazybytes.accounts.repository.CustomerRepo;
import com.eazybytes.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
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
        customer.setCreateBy("Prabhakar");
        Customer savedCustomer = customerRepo.save(customer);

        accountRepo.save(createNewAccount(savedCustomer));

    }


    private Accounts createNewAccount(Customer customer){
        Accounts newAccounts = new Accounts();

        newAccounts.setCustomerId(String.valueOf(customer.getCustomerId()));
        long randomNumber = 1000000000L + new Random().nextInt();

        newAccounts.setAccountNumber(randomNumber);
        newAccounts.setAccountType(AccountConstant.SAVINGS);
        newAccounts.setBranchAddress(AccountConstant.ADDRESS);
        newAccounts.setCreateBy("Prabhakar");
        newAccounts.setCreateBy("Prabhakar");
        newAccounts.setCreatedAt(LocalDateTime.now());
        return  newAccounts;
    }
}
