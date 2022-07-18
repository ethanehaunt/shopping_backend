package com.gupshup.shopping.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.gupshup.shopping.dao.MessageRepository;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    MessageRepository messageRepository;

    @Scheduled(fixedRate = 60*1000) 
    public void scheduleTask(){

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageRepository.fetchScheduleMessage(dateFormat.format(new Date()));
    }
}
