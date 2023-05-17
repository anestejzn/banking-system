package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.response.AccountTypeResponse;
import com.ftn.sbnz.tim5.service.dto.response.EmployerBasicResponse;
import com.ftn.sbnz.tim5.service.services.interfaces.IEmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    private IEmployerService employerService;

    @GetMapping("/all-employers")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployerBasicResponse> getAll() {

        return employerService.getAll();
    }

}
