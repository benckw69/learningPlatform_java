package com.benckw69.learningPlatform_java.AdminConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/referral")
    public String referralConfig(Referral referral){
        Referral referralDatabase = adminService.getReferralConfig();
        referral.setId(referralDatabase.getId());
        referral.setNewUserAmount(referralDatabase.getNewUserAmount());
        referral.setNewUserGet(referralDatabase.getNewUserGet());
        referral.setReferralAmount(referralDatabase.getReferralAmount());
        referral.setReferralGet(referralDatabase.getReferralGet());
        return "pages/admin_referral_config";
    }

    @PostMapping("/referral")
    public String referralConfig(@Valid Referral referral, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "pages/admin_referral_config";
        referral.setId(1);
        adminService.saveReferral(referral);
        return "redirect:/admin/referral?edit=true";
    }

}
