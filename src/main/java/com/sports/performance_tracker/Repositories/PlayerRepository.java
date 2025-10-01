package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String name);
}
