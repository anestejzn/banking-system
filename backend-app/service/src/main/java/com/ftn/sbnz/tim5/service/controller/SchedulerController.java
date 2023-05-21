package com.ftn.sbnz.tim5.service.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@Controller
public class SchedulerController {

    @Scheduled(cron = "0 0 0 L * *")
    public void scheduleGetMoneyForDebits(){

    }
}
