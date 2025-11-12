package com.sports.performance_tracker.Controllers;

import com.sports.performance_tracker.Service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService){
        this.analyticsService = analyticsService;
    }

    @GetMapping("/player/{id}/totals")
    public Map<String, Double> getPlayerTotals(@PathVariable Long id){
        return analyticsService.getPlayerTotals(id);
    }

    @GetMapping("/player/{id}/averages")
    public Map<String, Double> getPlayerAverages(@PathVariable Long id){
        return analyticsService.getPlayerAverages(id);
    }

    @GetMapping("/leaderboard/{metricKey}")
    public List<Map.Entry<String, Double>> getLeaderboard(@PathVariable String metricKey){
        return analyticsService.getLeaderboard(metricKey);
    }


}
