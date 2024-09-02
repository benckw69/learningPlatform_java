package com.benckw69.learningPlatform_java.MoneyTicket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTicketRepository extends JpaRepository<MoneyTicket,Integer> {
    //check whether there is duplicate money tickets
    public MoneyTicket findByTicketStringAndIsUsed(String tickeString, Boolean isUsed);

    public List<MoneyTicket> findByIsUsedOrderByIdDesc(Boolean isUsed);
}
