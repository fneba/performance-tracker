package com.sports.performance_tracker.Controllers;

import com.sports.performance_tracker.Models.Metric;
import com.sports.performance_tracker.Models.Player;
import com.sports.performance_tracker.Models.Session;
import com.sports.performance_tracker.Models.SessionMetricValue;
import com.sports.performance_tracker.Repositories.MetricRepository;
import com.sports.performance_tracker.Repositories.PlayerRepository;
import com.sports.performance_tracker.Repositories.SessionMetricValueRepository;
import com.sports.performance_tracker.Repositories.SessionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionRepository sessionRepository;
    private final PlayerRepository playerRepository;
    private final MetricRepository metricRepository;
    private final SessionMetricValueRepository sessionMetricValueRepository;

    public SessionController(SessionRepository sessionRepository, PlayerRepository playerRepository,
                             MetricRepository metricRepository, SessionMetricValueRepository sessionMetricValueRepository){
        this.sessionRepository = sessionRepository;
        this.playerRepository = playerRepository;
        this.metricRepository = metricRepository;
        this.sessionMetricValueRepository = sessionMetricValueRepository;
    }

    @GetMapping
    public List<Session> getAllSessions(){
        return sessionRepository.findAll();
    }

    @PostMapping
    public Session createSession(@RequestBody Map<String, String> request){
        Long playerId = Long.parseLong(request.get("playerId"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        Session session = new Session();
        session.setPlayer(player);
        session.setDate(LocalDate.parse(request.get("date"))); // format: "YYYY-MM-DD"
        session.setOpponent(request.get("opponent"));

        return sessionRepository.save(session);
    }

    @PostMapping("/{id}/metrics")
    public List<SessionMetricValue> addMetricsToSession(@PathVariable Long id, @RequestBody Map<String, Double> metrics){
        Session session = sessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Session not found"));

        for (Map.Entry<String, Double> entry : metrics.entrySet()){
            Metric metric = metricRepository.findByKey(entry.getKey());
            if (metric == null){
                throw new RuntimeException("Metric not found: " + entry.getKey());
            }

            SessionMetricValue smv = new SessionMetricValue();
            smv.setSession(session);
            smv.setMetric(metric);
            smv.setValue(entry.getValue());

            sessionMetricValueRepository.save(smv);
        }
        return sessionMetricValueRepository.findAll();
    }
}
