package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.SessionMetricValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionMetricValueRepository extends JpaRepository<SessionMetricValue, Long> {
}
