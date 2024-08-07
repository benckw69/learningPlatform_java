package com.benckw69.learningPlatform_java.AdminConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.benckw69.learningPlatform_java.User.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ReferralService referralServiceService;

    @Autowired
    UserService userService;

    @Autowired
    MoneyRecordService moneyRecordService;

    @GetMapping("/referral")
    public String referralConfig(Referral referral){
        Referral referralDatabase = referralServiceService.getReferralConfig();
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
        referralServiceService.saveReferral(referral);
        return "redirect:/admin/referral?edit=true";
    }

    @GetMapping("/userDeleteRecords")
    public String userDeleteRecordsView(Model model,HttpSession httpSession){
        model.addAttribute("deleteAcRecords", userService.getDeletedUsersOrderByDate());
        return "pages/admin_user_delete_records";
    }

    @PostMapping("/userDeleteRecords/{id}")
    public String invertUserDelete(@PathVariable Integer id){
        userService.resetDeletedUser(id);
        return "redirect:/admin/userDeleteRecords?reset=true";
    }

}
