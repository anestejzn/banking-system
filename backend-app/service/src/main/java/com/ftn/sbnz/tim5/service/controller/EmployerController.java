package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.request.EmployerRequest;
import com.ftn.sbnz.tim5.service.dto.response.EmployerBasicResponse;
import com.ftn.sbnz.tim5.service.dto.response.EmployerResponse;
import com.ftn.sbnz.tim5.service.services.interfaces.IEmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployerResponse saveNewEmployer(@RequestBody EmployerRequest employerRequest){
        return employerService.save(employerRequest.getName(), employerRequest.getPib(), employerRequest.getStartedOperating());
    }

}
