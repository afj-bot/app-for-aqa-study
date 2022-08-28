CREATE TABLE IF NOT EXISTS user_login_events
(
    id         BINARY(16)                          not null primary key,
    user_id    BINARY(16)                          not null,
    FOREIGN KEY (user_id) REFERENCES user (id),
    total      int,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP

    ) engine = InnoDB;

CREATE index FKf812pygec41i5jo6oduhrsd01
    ON user_login_events(user_id);