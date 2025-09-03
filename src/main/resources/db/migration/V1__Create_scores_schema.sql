-- Create 'rounds' table
CREATE TABLE rounds (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    started_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    handicap_before DOUBLE PRECISION,
    handicap_after DOUBLE PRECISION,
    status VARCHAR(20)
);

-- Create 'hole_score' table
CREATE TABLE hole_score (
    id SERIAL PRIMARY KEY,
    hole_number INT NOT NULL,
    strokes INT NOT NULL,
    putts INT,
    penalties INT,
    recorded_at TIMESTAMP NOT NULL,
    round_id BIGINT NOT NULL,
    CONSTRAINT fk_hole_score_round
        FOREIGN KEY (round_id) REFERENCES rounds(id)
        ON DELETE CASCADE
);

-- Create 'handicap_history' table
CREATE TABLE handicap_history (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    effective_date TIMESTAMP NOT NULL,
    handicap_before DOUBLE PRECISION NOT NULL,
    handicap_after DOUBLE PRECISION NOT NULL,
    round_id BIGINT NOT NULL
);