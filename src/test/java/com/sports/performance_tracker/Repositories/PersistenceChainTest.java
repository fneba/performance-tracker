package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.Metric;
import com.sports.performance_tracker.Models.Player;
import com.sports.performance_tracker.Models.Session;
import com.sports.performance_tracker.Models.SessionMetricValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;


import java.time.LocalDate;
import java.util.List;

@DataJpaTest
class PersistenceChainTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired private MetricRepository metricRepository;
    @Autowired private SessionRepository sessionRepository;
    @Autowired private SessionMetricValueRepository sessionMetricValueRepository;

    @Test
    void testPlayerSessionMetricPersistenceChain() {
        // Create and save a Player
        Player player = new Player();
        player.setName("Jalen Hurts");
        player.setPosition("QB");
        player.setTeam("Eagles");
        player = playerRepository.save(player);

        // Create and save a Metric
        Metric metric = new Metric();
        metric.setKey("passing_yards");
        metric.setDescription("Total passing yards");
        metric = metricRepository.save(metric);

        // Create and save a Session for that Player
        Session session = new Session();
        session.setPlayer(player);
        session.setDate(LocalDate.of(2025, 9, 27));
        session.setOpponent("Cowboys");
        session = sessionRepository.save(session);

        // Create and save a SessionMetricValue linking Session + Metric
        SessionMetricValue smv = new SessionMetricValue();
        smv.setSession(session);
        smv.setMetric(metric);
        smv.setValue(325.0);
        sessionMetricValueRepository.save(smv);

        // Retrieve and assert everything persisted correctly
        List<SessionMetricValue> results = sessionMetricValueRepository.findAll();
        assertThat(results).hasSize(1);

        SessionMetricValue retrieved = results.get(0);
        assertThat(retrieved.getValue()).isEqualTo(325.0);
        assertThat(retrieved.getMetric().getKey()).isEqualTo("passing_yards");
        assertThat(retrieved.getSession().getOpponent()).isEqualTo("Cowboys");
        assertThat(retrieved.getSession().getPlayer().getName()).isEqualTo("Jalen Hurts");
    }

}
