package com.sports.performance_tracker.Repositories;

import com.sports.performance_tracker.Models.Metric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MetricRepositoryTest {

    @Autowired
    private MetricRepository metricRepository;

    @Test
    void testFindByKey() {
        Metric metric = new Metric();
        metric.setKey("passing_yards");
        metric.setDescription("Total passing yards in a game");
        metricRepository.save(metric);

        Metric found = metricRepository.findByKey("passing_yards");
        assertThat(found).isNotNull();
        assertThat(found.getDescription()).isEqualTo("Total passing yards in a game");
    }
}