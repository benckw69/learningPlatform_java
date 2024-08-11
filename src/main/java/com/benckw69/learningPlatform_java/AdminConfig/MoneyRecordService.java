package com.benckw69.learningPlatform_java.AdminConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class MoneyRecordService {
    @Autowired
    private MoneyRecordRepository moneyRecordRepository;

    public List<MoneyRecord> getMoneyRecordByUserId(HttpSession httpSession){
        Integer userId = (Integer)httpSession.getAttribute("userId");
        return moneyRecordRepository.findByUserId(userId);
    }

    public MoneyRecord updateMoneyRecord(MoneyRecord moneyRecord){
        return moneyRecordRepository.save(moneyRecord);
    }
}
