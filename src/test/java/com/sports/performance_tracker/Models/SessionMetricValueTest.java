package com.sports.performance_tracker.Models;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SessionMetricValueTest {

    @Test
    void testSessionMetricValueFields() {
        Metric metric = new Metric();
        metric.setKey("rushing_yards");

        Session session = new Session();
        session.setOpponent("Giants");

        SessionMetricValue smv = new SessionMetricValue();
        smv.setId(100L);
        smv.setMetric(metric);
        smv.setSession(session);
        smv.setValue(87.5);

        assertThat(smv.getId()).isEqualTo(100L);
        assertThat(smv.getMetric().getKey()).isEqualTo("rushing_yards");
        assertThat(smv.getSession().getOpponent()).isEqualTo("Giants");
        assertThat(smv.getValue()).isEqualTo(87.5);
    }
}