package com.benckw69.learningPlatform_java.MoneyTicket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.benckw69.learningPlatform_java.AdminConfig.EventCategory;
import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecord;
import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecordRepository;
import com.benckw69.learningPlatform_java.User.User;
import com.benckw69.learningPlatform_java.User.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class MoneyTicketService {
    @Autowired
    MoneyTicketRepository moneyTicketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MoneyRecordRepository moneyRecordRepository;

    public Boolean validInsertMoneyTicket(MoneyTicket moneyTicket,Model model){
        MoneyTicket existMoneyTicket = moneyTicketRepository.findByTicketStringAndIsUsed(moneyTicket.getTicketString(), false);
        if(existMoneyTicket == null) return true;
        else {
            model.addAttribute("ticketStringError", "此號碼已被使用");
            return false;
        }
    }

    public void insertMoneyTickets(MoneyTicket moneyTicket){
        moneyTicketRepository.save(moneyTicket);
    }

    public List<MoneyTicket> findAllAvailableMoneyTickets(){
        return moneyTicketRepository.findByisUsedOrderByIdDesc(false);
    }

    public void deleteMoneyTicket(Integer id){
        moneyTicketRepository.deleteById(id);
    }

    public Boolean validMoneyTicket(MoneyTicket moneyTickets, Model model){
        MoneyTicket existMoneyTicket = moneyTicketRepository.findByTicketStringAndIsUsed(moneyTickets.getTicketString(), false);
        if(existMoneyTicket != null) return true;
        else {
            model.addAttribute("ticketStringError", "號碼錯誤");
            return false;
        }
    }

    public void useMoneyTicket(MoneyTicket moneyTickets, HttpSession httpSession){
        //set the ticket to used.  Set new balance for the user.  Set message for money transaction.
        MoneyTicket moneyTicket = moneyTicketRepository.findByTicketStringAndIsUsed(moneyTickets.getTicketString(), false);
        moneyTicket.setIsUsed(true);
        User user = (User)httpSession.getAttribute("user");
        moneyTicket.setUser(user);
        user.setBalance(user.getBalance()+moneyTicket.getAmount());

        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setEventText(EventCategory.ADD_MONEY, user, moneyTicket);
        moneyRecord.setEventCategory(EventCategory.ADD_MONEY);
        moneyRecord.setMoneyChange(moneyTicket.getAmount());
        moneyRecord.setUser(user);
        moneyRecord.setEventConsequence(1);

        moneyTicketRepository.save(moneyTicket);
        userRepository.save(user);
        moneyRecordRepository.save(moneyRecord);
    }
}
