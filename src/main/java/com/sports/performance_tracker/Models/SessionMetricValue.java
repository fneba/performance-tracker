package com.sports.performance_tracker.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "session_metric_values")
public class SessionMetricValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(optional = false)
    @JoinColumn(name = "metric_id", nullable = false)
    private Metric metric;

    @Column(nullable = false)
    private Double value;

    public SessionMetricValue() {}

    // Getters
    public Long getId() { return id; }
    public Session getSession() { return session; }
    public Metric getMetric() { return metric; }
    public Double getValue() { return value; }


    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSession(Session session) { this.session = session; }
    public void setMetric(Metric metric) { this.metric = metric; }
    public void setValue(Double value) { this.value = value; }
}
