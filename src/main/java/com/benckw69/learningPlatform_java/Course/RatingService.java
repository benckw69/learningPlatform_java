package com.benckw69.learningPlatform_java.Course;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public Rating getRating(BuyRecord buyRecord){
        return ratingRepository.findByBuyRecord(buyRecord);
    }

    public Rating updateRating(Rating rating){
        return ratingRepository.save(rating);
    }

    public List<Rating> getRatingsByBuyRecords(Set<BuyRecord> buyRecords){
        return ratingRepository.findByBuyRecordIn(buyRecords);
    }
}
