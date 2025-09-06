CREATE TABLE shots (
    id BIGSERIAL PRIMARY KEY,
    shot_number INT NOT NULL,
    club VARCHAR(20) NOT NULL,
    hole_score_id BIGINT NOT NULL,
    distance_yards INTEGER,
    CONSTRAINT fk_shot_hole_score FOREIGN KEY (hole_score_id) REFERENCES hole_score(id) ON DELETE CASCADE
);