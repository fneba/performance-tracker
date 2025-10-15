package com.sports.performance_tracker.Service;

import com.sports.performance_tracker.Models.Metric;
import com.sports.performance_tracker.Models.Player;
import com.sports.performance_tracker.Models.Session;
import com.sports.performance_tracker.Models.SessionMetricValue;
import com.sports.performance_tracker.Repositories.MetricRepository;
import com.sports.performance_tracker.Repositories.PlayerRepository;
import com.sports.performance_tracker.Repositories.SessionMetricValueRepository;
import com.sports.performance_tracker.Repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final PlayerRepository playerRepository;
    private final MetricRepository metricRepository;
    private final SessionMetricValueRepository sessionMetricValueRepository;

    public SessionService(SessionRepository sessionRepository,
                          PlayerRepository playerRepository,
                          MetricRepository metricRepository,
                          SessionMetricValueRepository sessionMetricValueRepository){
        this.sessionRepository = sessionRepository;
        this.metricRepository = metricRepository;
        this.playerRepository = playerRepository;
        this.sessionMetricValueRepository = sessionMetricValueRepository;
    }

    // Create a new game (session)
    public Session createSession(Long playerId, String date, String opponent) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Session session = new Session();
        session.setPlayer(player);
        session.setDate(LocalDate.parse(date));
        session.setOpponent(opponent);

        return sessionRepository.save(session);
    }

    // Add stats (metrics) for a specific game
    public List<SessionMetricValue> addMetricsToSession(Long sessionId, Map<String, Double> metrics) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            // Whole-number enforcement for touchdowns & interceptions
            if (key.endsWith("_tds") || key.endsWith("_ints")) {
                if (value % 1 != 0) {
                    throw new IllegalArgumentException(key + " must be a whole number.");
                }
            }

            // Rounding for yardage metrics (half-up)
            if (key.endsWith("_yards")) {
                value = (double) Math.round(value);
            }

            Metric metric = metricRepository.findByKey(key);
            if (metric == null) {
                throw new RuntimeException("Metric not found: " + key);
            }

            SessionMetricValue smv = new SessionMetricValue();
            smv.setSession(session);
            smv.setMetric(metric);
            smv.setValue(value);

            sessionMetricValueRepository.save(smv);
        }

        return sessionMetricValueRepository.findAll();
    }

    // Get all sessions (for listing)
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }
}
