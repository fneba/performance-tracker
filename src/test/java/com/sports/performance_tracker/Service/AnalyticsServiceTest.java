package com.sports.performance_tracker.Service;

import com.sports.performance_tracker.Models.Metric;
import com.sports.performance_tracker.Models.Player;
import com.sports.performance_tracker.Repositories.MetricRepository;
import com.sports.performance_tracker.Repositories.PlayerRepository;
import com.sports.performance_tracker.Repositories.SessionMetricValueRepository;
import com.sports.performance_tracker.Repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AnalyticsService.class)
class AnalyticsServiceTest {

    @Autowired private AnalyticsService analyticsService;

    @Autowired private PlayerRepository playerRepository;
    @Autowired private MetricRepository metricRepository;
    @Autowired private SessionRepository sessionRepository;
    @Autowired private SessionMetricValueRepository sessionMetricValueRepository;

    private Player hurts;
    private Player tua;

    private Metric passingYards;
    private Metric passingTds;

    @BeforeEach
    void setUp() {
        // Players
        hurts = new Player();
        hurts.setName("Jalen Hurts");
        hurts.setPosition("QB");
        hurts.setTeam("Eagles");
        hurts = playerRepository.save(hurts);

        tua = new Player();
        tua.setName("Tua Tagovailoa");
        tua.setPosition("QB");
        tua.setTeam("Dolphins");
        tua = playerRepository.save(tua);

        // Metrics
        passingYards = new Metric();
        passingYards.setKey("passing_yards");
        passingYards.setDescription("Total passing yards");
        passingYards = metricRepository.save(passingYards);

        passingTds = new Metric();
        passingTds.setKey("passing_tds");
        passingTds.setDescription("Passing touchdowns");
        passingTds = metricRepository.save(passingTds);
    }
}