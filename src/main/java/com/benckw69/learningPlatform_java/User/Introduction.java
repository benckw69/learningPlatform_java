package com.benckw69.learningPlatform_java.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="introduction")
public class Introduction {
    @Id
    @Column(name = "user_id")
    private Integer id;
    private String introduction;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    public User user;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public void setDefaultIntroduction(User user) {
        this.introduction = "Hello I am "+user.getUsername()+".";
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Introduction(){}
}
