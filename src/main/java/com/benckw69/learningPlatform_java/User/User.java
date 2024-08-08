package com.benckw69.learningPlatform_java.User;

import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecord;
import com.benckw69.learningPlatform_java.Course.BuyRecord;
import com.benckw69.learningPlatform_java.Course.Course;
import com.benckw69.learningPlatform_java.MoneyTicket.MoneyTicket;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    @Column(nullable = false, length = 256)
    private String email;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name="login_method", nullable = false)
    private LoginMethod loginMethod;
    @Column(name="specific_id", length = 64)
    private String specificId = null;
    @Column(nullable = false)
    private int balance = 0;
    @Column(name = "is_deleted", nullable = false, columnDefinition="tinyint(1) default 0")
    private Boolean isDeleted = false;
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdTime;
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Timestamp updatedTime;

    @OneToOne(targetEntity = Introduction.class, mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Introduction introduction;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MoneyRecord> moneyRecords;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MoneyTicket> moneyTickets;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Course> course;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<BuyRecord> buyRecord;

    public Introduction getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Introduction introduction) {
        this.introduction = introduction;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginMethod(LoginMethod loginMethod) {
        this.loginMethod = loginMethod;
    }

    public void setSpecificId(String specificId) {
        this.specificId = specificId;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getBalance() {
        return balance;
    }

    public Integer getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginMethod getLoginMethod() {
        return loginMethod;
    }

    public String getSpecificId() {
        return specificId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public User(){}

    @Override
    public String toString() {
        return "User [id=" + id + ", type=" + type + ", email=" + email + ", username=" + username + ", password="
                + password + ", loginMethod=" + loginMethod + ", specificId=" + specificId + ", balance=" + balance
                + ", isDeleted=" + isDeleted + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime
                + "]";
    }

}