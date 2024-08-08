package com.benckw69.learningPlatform_java.AdminConfig;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="money_seperation")
public class MoneySeperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "teacher_money_percentage", nullable = false)
    @NotNull
    @Min(value = 0, message = "百分比不可小於 0")
    @Max(value = 100, message = "百分比不可大於 100")
    Integer teacherMoneyPercentage;
    
    @Override
    public String toString() {
        return "MoneySeperation [id=" + id + ", teacherMoneyPercentage=" + teacherMoneyPercentage + "]";
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getTeacherMoneyPercentage() {
        return teacherMoneyPercentage;
    }
    public void setTeacherMoneyPercentage(Integer teacherMoneyPercentage) {
        this.teacherMoneyPercentage = teacherMoneyPercentage;
    }
}
