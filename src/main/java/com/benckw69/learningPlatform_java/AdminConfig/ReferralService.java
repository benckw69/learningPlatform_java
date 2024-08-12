package com.benckw69.learningPlatform_java.AdminConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferralService {
    @Autowired
    private ReferralRepository referralRepository;

    public Referral getReferralConfig(){
        return referralRepository.findById(1).orElse(null);
    }

    public void saveReferral(Referral referral){
        referralRepository.save(referral);
    }

    public void init() {
        if(getReferralConfig()==null){
            Referral referral = new Referral();
            referral.setReferralAmount(10);
            referral.setReferralGet(true);
            referral.setNewUserAmount(10);
            referral.setNewUserGet(true);
            saveReferral(referral);
        }
    }
}
