package com.benckw69.learningPlatform_java.Course;

import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.benckw69.learningPlatform_java.User.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 256)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "people_suite")
    private String peopleSuite;

    @Column(length = 4096)
    private String introduction;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name="video_type")
    private VideoType videoType;

    @Enumerated(EnumType.STRING)
    @Column(name="photo_type")
    private PhotoType photoType;

    @Column(nullable = false)
    private Integer price = 0;

    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdTime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private Timestamp updatedTime;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<BuyRecord> buyRecord;
   
    public String getPeopleSuite() {
        return peopleSuite;
    }
    public void setPeopleSuite(String peopleSuite) {
        this.peopleSuite = peopleSuite;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<BuyRecord> getBuyRecord() {
        return buyRecord;
    }
    public void setBuyRecord(Set<BuyRecord> buyRecord) {
        this.buyRecord = buyRecord;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public VideoType getVideoType() {
        return videoType;
    }
    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }
    public PhotoType getPhotoType() {
        return photoType;
    }
    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
}
