package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.services.implementation.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@Controller
public class SchedulerController {
    @Autowired
    private SchedulerService schedulerService;
    @Scheduled(cron = "0 0 0 L * *")
    public void scheduleGetMoneyForDebits(){
        schedulerService.getMoneyForDebits();
    }
}
