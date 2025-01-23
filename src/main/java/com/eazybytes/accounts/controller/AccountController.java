package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.Dto.CustomerDto;
import com.eazybytes.accounts.Dto.ResponseDto;
import com.eazybytes.accounts.constant.AccountConstant;
import com.eazybytes.accounts.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {

    @Autowired
    private IAccountService iAccountService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello User";
    }





//    @PostMapping("/create")
//    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
//
//        iAccountService.createAccount(customerDto);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201));
//
//    }

}
