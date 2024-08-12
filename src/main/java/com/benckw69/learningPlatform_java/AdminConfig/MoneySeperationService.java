package com.benckw69.learningPlatform_java.AdminConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneySeperationService {

    @Autowired
    MoneySeperationRepository moneySeperationRepository;

    public MoneySeperation getMoneySeperation(){
        return moneySeperationRepository.findById(1).orElse(null);
    }

    public void editMoneySeperation(MoneySeperation moneySeperation){
        moneySeperation.setId(1);
        moneySeperationRepository.save(moneySeperation);
    }

    public void init(){
        if(getMoneySeperation() == null) {
            MoneySeperation moneySeperation = new MoneySeperation();
            moneySeperation.setTeacherMoneyPercentage(80);
            editMoneySeperation(moneySeperation);
        }
    }
}
