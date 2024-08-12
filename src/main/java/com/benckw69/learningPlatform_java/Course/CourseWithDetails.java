package com.benckw69.learningPlatform_java.Course;

public class CourseWithDetails extends Course {
    private Double rate;
    private Integer noOfRates;
    private Integer noOfStudents;

   

    public Integer getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(Integer noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public Integer getNoOfRates() {
        return noOfRates;
    }

    public void setNoOfRates(Integer noOfRates) {
        this.noOfRates = noOfRates;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    CourseWithDetails(Course course){
        this.setId(course.getId());
        this.setBuyRecord(course.getBuyRecord());
        this.setCategory(course.getCategory());
        this.setContent(course.getContent());
        this.setCreatedTime(course.getCreatedTime());
        this.setIntroduction(course.getIntroduction());
        this.setIsDeleted(course.getIsDeleted());
        this.setPeopleSuite(course.getPeopleSuite());
        this.setPhotoType(course.getPhotoType());
        this.setPrice(course.getPrice());
        this.setTitle(course.getTitle());
        this.setUpdatedTime(course.getUpdatedTime());
        this.setUser(course.getUser());
        this.setVideoType(course.getVideoType());
    }

}
