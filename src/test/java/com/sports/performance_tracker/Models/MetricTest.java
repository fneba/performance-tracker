package com.sports.performance_tracker.Models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MetricTest {

    @Test
    void testMetricFields() {
        Metric metric = new Metric();
        metric.setId(10L);
        metric.setKey("passing_yards");
        metric.setDescription("Total passing yards in a game");

        assertThat(metric.getId()).isEqualTo(10L);
        assertThat(metric.getKey()).isEqualTo("passing_yards");
        assertThat(metric.getDescription()).contains("Total passing yards");
    }
}