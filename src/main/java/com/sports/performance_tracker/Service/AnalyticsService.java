package com.sports.performance_tracker.Service;

import com.sports.performance_tracker.Models.SessionMetricValue;
import com.sports.performance_tracker.Repositories.PlayerRepository;
import com.sports.performance_tracker.Repositories.SessionMetricValueRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final PlayerRepository playerRepository;
    private final SessionMetricValueRepository sessionMetricValueRepository;

    public AnalyticsService(PlayerRepository playerRepository, SessionMetricValueRepository sessionMetricValueRepository){
        this.playerRepository = playerRepository;
        this.sessionMetricValueRepository = sessionMetricValueRepository;
    }

    public Map<String, Double> getPlayerTotals(Long playerId) {
        List<SessionMetricValue> values = sessionMetricValueRepository.findAll()
                .stream()
                .filter(v -> v.getSession().getPlayer().getId().equals(playerId))
                .toList();

        return values.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMetric().getKey(),
                        Collectors.summingDouble(SessionMetricValue::getValue)
                ));
    }

    public Map<String, Double> getPlayerAverages(Long playerId){
        List<SessionMetricValue> values = sessionMetricValueRepository.findAll()
                .stream()
                .filter(v -> v.getSession().getPlayer().getId().equals(playerId))
                .toList();

        Map<String, List<Double>> grouped = values.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMetric().getKey(),
                        Collectors.mapping(SessionMetricValue::getValue, Collectors.toList())
                ));

        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : grouped.entrySet()){
            List<Double> vals = entry.getValue();
            double avg = vals.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            averages.put(entry.getKey(), avg);
        }

        return averages;
    }

    public List<Map.Entry<String, Double>> getLeaderboard(String metricKey){
        List<SessionMetricValue> values = sessionMetricValueRepository.findAll()
                .stream()
                .filter(v -> v.getMetric().getKey().equals(metricKey))
                .toList();

        Map<String, Double> totalsByPlayer = new HashMap<>();
        for (SessionMetricValue v : values){
            String playerName = v.getSession().getPlayer().getName();
            totalsByPlayer.merge(playerName, v.getValue(), Double::sum);
        }

        return totalsByPlayer.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .toList();
    }
}
