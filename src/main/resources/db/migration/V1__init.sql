-- Sports Performance Tracker Schema
-- Migration: V1__init.sql

-- USERS
create table players (
    id serial primary key,
    name varchar(255) not null,
    football_position varchar(50),  -- e.g. QB, WR, LB
    team varchar(50)
);

-- METRICS (e.g. shots_made, shots_attempted, etc.)
create table metrics (
    id serial primary key,
    metric_key varchar(100) unique not null, -- e.g "passing_yards"
    description TEXT
);

-- SESSIONS (one per practice/game/workout)
create table sessions (
    id serial primary key,
    player_id bigint not null references players(id),
    date_date DATE not null,
    opponent varchar(100)
);

-- SESSION_METRIC_VALUES (holds the numbers for a session)
create table session_metric_values (
    id serial primary key,
    session_id bigint not null references sessions(id) on delete cascade,
    metric_id bigint not null references metrics(id),
    value double precision not null
);

-- INDEXES
create index idx_session_metric on session_metric_values(session_id, metric_id);
