package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.services.implementation.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-cep")
public class TestCepController {

    @Autowired
    private SchedulerService schedulerService;
    @GetMapping("/get-money")
    public void scheduleGetMoneyForDebits(){
        schedulerService.getMoneyForDebits();
    }
}
