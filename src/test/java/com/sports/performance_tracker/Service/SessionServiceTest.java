package com.sports.performance_tracker.Service;

import com.sports.performance_tracker.Models.*;
import com.sports.performance_tracker.Repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(SessionService.class) // imports service while still using in-memory DB
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMetricValueRepository sessionMetricValueRepository;

    private Player player;
    private Metric yardsMetric;
    private Metric tdsMetric;

    @BeforeEach
    void setup() {
        // Save player
        player = new Player();
        player.setName("Jalen Hurts");
        player.setPosition("QB");
        player.setTeam("Eagles");
        player = playerRepository.save(player);

        // Save metrics
        yardsMetric = new Metric();
        yardsMetric.setKey("passing_yards");
        yardsMetric.setDescription("Total passing yards");
        metricRepository.save(yardsMetric);

        tdsMetric = new Metric();
        tdsMetric.setKey("passing_tds");
        tdsMetric.setDescription("Passing touchdowns");
        metricRepository.save(tdsMetric);
    }

    @Test
    void testCreateSession() {
        Session session = sessionService.createSession(player.getId(), "2025-09-27", "Cowboys");

        assertThat(session.getId()).isNotNull();
        assertThat(session.getPlayer().getName()).isEqualTo("Jalen Hurts");
        assertThat(session.getOpponent()).isEqualTo("Cowboys");
    }

    @Test
    void testRoundingForYardageMetrics() {
        Session session = sessionService.createSession(player.getId(), "2025-09-27", "Giants");

        Map<String, Double> metrics = new HashMap<>();
        metrics.put("passing_yards", 312.5); // should round up

        sessionService.addMetricsToSession(session.getId(), metrics);

        List<SessionMetricValue> values = sessionMetricValueRepository.findAll();
        assertThat(values).hasSize(1);
        assertThat(values.get(0).getValue()).isEqualTo(313.0); // rounded
    }

    @Test
    void testWholeNumberEnforcementForTouchdowns() {
        Session session = sessionService.createSession(player.getId(), "2025-09-27", "Commanders");

        Map<String, Double> metrics = new HashMap<>();
        metrics.put("passing_tds", 3.7); // invalid (not whole number)

        assertThatThrownBy(() -> sessionService.addMetricsToSession(session.getId(), metrics))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be a whole number");
    }

    @Test
    void testAcceptsValidWholeNumberTouchdowns() {
        Session session = sessionService.createSession(player.getId(), "2025-09-27", "Bills");

        Map<String, Double> metrics = new HashMap<>();
        metrics.put("passing_tds", 4.0); // valid integer

        sessionService.addMetricsToSession(session.getId(), metrics);

        List<SessionMetricValue> values = sessionMetricValueRepository.findAll();
        assertThat(values).hasSize(1);
        assertThat(values.get(0).getValue()).isEqualTo(4.0);
    }
}