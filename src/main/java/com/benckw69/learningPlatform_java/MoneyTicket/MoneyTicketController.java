package com.benckw69.learningPlatform_java.MoneyTicket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class MoneyTicketController {
    @Autowired
    MoneyTicketService moneyTicketService;

    @GetMapping("/moneyTicket/use")
    public String useMoneyTickets(MoneyTicket moneyTicket){
        return "pages/user_money_ticket_use";
    }

    @PostMapping("/moneyTicket/use")
    public String useMoneyTickets(MoneyTicket moneyTicket, Model model, HttpSession httpSession){
        Boolean validation = moneyTicketService.validMoneyTicket(moneyTicket, model);
        if(!validation) return "pages/user_money_ticket_use";
        Boolean success = moneyTicketService.useMoneyTicket(moneyTicket,httpSession);
        if(success) return "redirect:/user/moneyTicket/use?use=true";
        else return "redirect:/logout";
        
    }
}