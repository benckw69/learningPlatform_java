package com.benckw69.learningPlatform_java.AdminConfig;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRecordRepository extends JpaRepository<MoneyRecord,Integer> {
    List<MoneyRecord> findByUserId(Integer Id);
}