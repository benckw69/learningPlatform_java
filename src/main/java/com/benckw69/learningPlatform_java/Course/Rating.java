package com.benckw69.learningPlatform_java.Course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @Column(name = "buy_record_id")
    private Integer id;
    @Column(nullable = false)
    @Min(value = 0, message = "最低評分不可低於 0")
    @Max(value = 5, message = "最高評分不可高於 5")
    @NotNull
    private Integer rate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "buy_record_id")
    public BuyRecord buyRecord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public BuyRecord getBuyRecord() {
        return buyRecord;
    }

    public void setBuyRecord(BuyRecord buyRecord) {
        this.buyRecord = buyRecord;
    }
}
