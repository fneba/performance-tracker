-- Sports Performance Tracker Schema
-- Migration: V1__init.sql

-- USERS
create table users (
    id bigserial primary key,
    email varchar(255) not null unique,
    password_hash varchar(255) not null,
    created_at timestamp default now()
);

-- SPORTS
create table sports (
    id bigserial primary key,
    name varchar(255) not null unique
);

-- METRICS (e.g. shots_made, shots_attempted, etc.)
create table metrics (
    id bigserial primary key,
    sport_id bigint not null references sports(id) on delete cascade,
    metric_key varchar(100) not null,
    name varchar(255) not null,
    type varchar(50) not null,
    unique(sport_id, metric_key)
);

-- SESSIONS (one per practice/game/workout)
create table sessions (
    id bigserial primary key,
    user_id bigint not null references users(id) on delete cascade,
    sport_id bigint not null references sports(id),
    started_at timestamp not null,
    duration_min int,
    notes text,
    created_at timestamp default now()
);

-- SESSION_METRIC_VALUES (holds the numbers for a session)
create table session_metric_values (
    id bigserial primary key,
    session_id bigint not null references sessions(id) on delete cascade,
    metric_id bigint not null references metrics(id),
    value_numeric decimal(12,4),
    created_at timestamp default now(),
    unique(session_id, metric_id)
);

-- INDEXES
create index idx_sessions_user_id on sessions(user_id);
create index idx_sessions_sport_id on sessions(sport_id);
create index idx_metrics_sport_id on metrics(sport_id);
create index idx_session_values_session_id on session_metric_values(session_id);