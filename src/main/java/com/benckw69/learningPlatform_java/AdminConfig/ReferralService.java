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
}
