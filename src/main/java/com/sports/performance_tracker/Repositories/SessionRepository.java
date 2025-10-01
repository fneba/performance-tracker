package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

}
