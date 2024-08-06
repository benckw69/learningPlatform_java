package com.benckw69.learningPlatform_java.AdminConfig;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="referral")
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "new_user_get", nullable = false, columnDefinition="tinyint(1) default 0")
    private Boolean newUserGet;
    @Column(name = "new_user_amount", nullable = false)
    @NotNull(message = "此欄不得為空")
    private Integer newUserAmount;
    @Column(name = "referral_get", nullable = false, columnDefinition="tinyint(1) default 0")
    private Boolean referralGet;
    @Column(name = "referral_amount", nullable = false)
    @NotNull(message = "此欄不得為空")
    private Integer referralAmount;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getNewUserGet() {
        return newUserGet;
    }
    public void setNewUserGet(Boolean newUserGet) {
        this.newUserGet = newUserGet;
    }
    public Integer getNewUserAmount() {
        return newUserAmount;
    }
    public void setNewUserAmount(Integer newUserAmount) {
        this.newUserAmount = newUserAmount;
    }
    public Boolean getReferralGet() {
        return referralGet;
    }
    public void setReferralGet(Boolean referralGet) {
        this.referralGet = referralGet;
    }
    public Integer getReferralAmount() {
        return referralAmount;
    }
    public void setReferralAmount(Integer referralAmount) {
        this.referralAmount = referralAmount;
    }
}
