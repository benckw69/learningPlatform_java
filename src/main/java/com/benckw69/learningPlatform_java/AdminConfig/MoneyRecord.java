package com.benckw69.learningPlatform_java.AdminConfig;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.benckw69.learningPlatform_java.Course.Course;
import com.benckw69.learningPlatform_java.MoneyTicket.MoneyTicket;
import com.benckw69.learningPlatform_java.User.Type;
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
    public void setEventText(EventCategory eventCategory, User newUser, User referral, Referral referralSetting){
        if(eventCategory == EventCategory.REFERRAL_BONUS){
            this.eventText = "用戶 "+newUser.getUsername()+"，編號為 "+newUser.getId()+" 獲得新用戶獎勵 $"+referralSetting.getNewUserAmount()+"，推薦者為用戶 "+referral.getUsername()+"，編號為 "+referral.getId()+"。";
        }
    }

    //referral bonus for referral
    public void setEventText(EventCategory eventCategory, User referral, Referral referralSetting, User newUser){
        if(eventCategory == EventCategory.REFERRAL_BONUS){
            this.eventText = "用戶 "+referral.getUsername()+"，編號為 "+referral.getId()+" 獲得推薦獎勵 $"+referralSetting.getReferralAmount()+"，新用戶為 "+newUser.getUsername()+"，編號為 "+newUser.getId()+"。";
        }
    }

    //add money to
    public void setEventText(EventCategory eventCategory, User user, MoneyTicket moneyTicket){
        if(eventCategory == EventCategory.ADD_MONEY){
            this.eventText = "用戶 "+user.getUsername()+"，編號為 "+user.getId()+"，増值 $"+moneyTicket.getAmount()+"，號碼為 "+moneyTicket.getTicketString()+"，編號為 "+moneyTicket.getId()+"。";
        }
    }

    //three more conditions for buying course
    public void setEventText(EventCategory eventCategory, User student, Course course){
        if(eventCategory == EventCategory.BUY_COURSE){
            if(user.getType() == Type.student){
                 this.eventText = "用戶 "+student.getUsername()+"，編號為 "+student.getId()+"，購買了編號為 "+course.getId()+"，名稱為 "+course.getTitle()+" 的課程，付費了 $"+course.getPrice()+"。";
            }
        }
    }

    public void setEventText(EventCategory eventCategory, User student, User teacherOrAdmin, Course course, MoneySeperation moneySeperation){
        if(eventCategory == EventCategory.BUY_COURSE){
            Integer teacherMoney = course.getPrice() * moneySeperation.getTeacherMoneyPercentage() /100;
            Integer adminMoney = course.getPrice() - teacherMoney;
            if(user.getType() == Type.admin){
                this.eventText = "用戶 "+student.getUsername()+"，編號為 "+student.getId()+"，購買了編號為 "+course.getId()+"，名稱為 "+course.getTitle()+" 的課程，編號為 "+teacherOrAdmin.getId()+"，名稱為 "+teacherOrAdmin.getUsername()+" 的管理員從價錢為 $"+course.getPrice()+" 的課程獲得 "+(100 - moneySeperation.getTeacherMoneyPercentage())+"% 的分成，獲得 $"+adminMoney+"。";
            } else if (user.getType() == Type.teacher){
                this.eventText = "用戶 "+student.getUsername()+"，編號為 "+student.getId()+"，購買了編號為 "+course.getId()+"，名稱為 "+course.getTitle()+" 的課程，編號為 "+teacherOrAdmin.getId()+"，名稱為 "+teacherOrAdmin.getUsername()+" 的導師從價錢為 $"+course.getPrice()+" 的課程獲得 "+moneySeperation.getTeacherMoneyPercentage()+"% 的分成，獲得 $"+teacherMoney+"。";
            }
        }
    }
}
