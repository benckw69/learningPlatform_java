package com.benckw69.learningPlatform_java.MoneyTicket;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.benckw69.learningPlatform_java.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="money_ticket")
public class MoneyTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotNull
    private Integer amount;
    @Column(name="ticket_string", nullable = false)
    @NotEmpty
    private String ticketString;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "is_used", nullable = false, columnDefinition="tinyint(1) default 0")
    private Boolean isUsed = false;
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdTime;
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Timestamp updatedTime;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Boolean getIsUsed() {
        return isUsed;
    }
    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
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
    @Override
    public String toString() {
        return "MoneyTicket [id=" + id + ", amount=" + amount + ", ticketString=" + ticketString + ", user=" + user
                + ", isUsed=" + isUsed + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + "]";
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getTicketString() {
        return ticketString;
    }
    public void setTicketString(String ticketString) {
        this.ticketString = ticketString;
    }
}
