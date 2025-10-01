package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    Metric findByKey(String key);
}
