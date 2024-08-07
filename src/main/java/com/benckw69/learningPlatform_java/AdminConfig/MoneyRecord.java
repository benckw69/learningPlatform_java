package com.benckw69.learningPlatform_java.AdminConfig;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.benckw69.learningPlatform_java.MoneyTicket.MoneyTicket;
import com.benckw69.learningPlatform_java.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "money_record")
public class MoneyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="money_change", nullable = false)
    private Integer moneyChange;
    @Column(name="event_consequence", nullable = false)
    private Integer eventConsequence;
    @Enumerated(EnumType.STRING)
    @Column(name = "event_category", nullable = false)
    private EventCategory eventCategory;
    @Column(name = "event_text", nullable = false, length=1000)
    private String eventText;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(nullable = false, columnDefinition="tinyint(1) default 0")
    private Boolean reverse = false;
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp created_time;
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Timestamp updated_time;

    @Override
    public String toString() {
        return "MoneyRecord [id=" + id + ", moneyChange=" + moneyChange + ", eventConsequence=" + eventConsequence
                + ", eventCategory=" + eventCategory + ", eventText=" + eventText + ", user.id=" + user.getId() + ", reverse="
                + reverse + ", created_time=" + created_time + ", updated_time=" + updated_time + "]";
    }
    public Timestamp getCreated_time() {
        return created_time;
    }
    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }
    public Timestamp getUpdated_time() {
        return updated_time;
    }
    public void setUpdated_time(Timestamp updated_time) {
        this.updated_time = updated_time;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMoneyChange() {
        return moneyChange;
    }
    public void setMoneyChange(Integer moneyChange) {
        this.moneyChange = moneyChange;
    }
    public Integer getEventConsequence() {
        return eventConsequence;
    }
    public void setEventConsequence(Integer eventConsequence) {
        this.eventConsequence = eventConsequence;
    }
    public EventCategory getEventCategory() {
        return eventCategory;
    }
    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }
    public String getEventText() {
        return eventText;
    }
    public void setEventText(String eventText) {
        this.eventText = eventText;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Boolean getReverse() {
        return reverse;
    }
    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }

    //referral bonus for new user
    public void setEventText(EventCategory eventCategory, User user, User user2, Referral referral){
        if(eventCategory == EventCategory.REFERRAL_BONUS){
            this.eventText = "用戶 "+user.getUsername()+" 獲得新用戶獎勵 $"+referral.getNewUserAmount()+"，推薦者為用戶 "+user2.getUsername()+"。";
        }
    }

    //referral bonus for referral
    public void setEventText(EventCategory eventCategory, User user, Referral referral, User user2){
        if(eventCategory == EventCategory.REFERRAL_BONUS){
            this.eventText = "用戶 "+user.getUsername()+" 獲得推薦獎勵 $"+referral.getReferralAmount()+"，新用戶為 "+user2.getUsername()+"。";
        }
    }

    //
    public void setEventText(EventCategory eventCategory, User user, MoneyTicket moneyTicket){
        if(eventCategory == EventCategory.ADD_MONEY){
            this.eventText = "用戶 "+user.getUsername()+" 増值 $"+moneyTicket.getAmount()+"，號碼為 "+moneyTicket.getTicketString()+"。";
        }
    }

    //three more conditions for buying course
}
