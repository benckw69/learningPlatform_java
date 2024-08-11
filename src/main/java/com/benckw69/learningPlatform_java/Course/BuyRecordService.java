package com.benckw69.learningPlatform_java.Course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benckw69.learningPlatform_java.User.User;

@Service
public class BuyRecordService {
    @Autowired
    BuyRecordRepository buyRecordRepository;

    List<BuyRecord> findBuyRecordByUserId(User user){
        return buyRecordRepository.findByUserOrderByCreatedTimeDesc(user);
    };
}
