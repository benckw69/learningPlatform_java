package com.benckw69.learningPlatform_java.Course;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    public Rating findByBuyRecord(BuyRecord buyRecord);

    public List<Rating> findByBuyRecordIn(Set<BuyRecord> buyrecords);
}
