ALTER TABLE user
    ADD privacy_policy TINYINT AFTER date_of_birth;

UPDATE user
SET privacy_policy = 1;

