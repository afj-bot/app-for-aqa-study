CREATE TABLE IF NOT EXISTS temporary_token
(
    id         BINARY(16) PRIMARY KEY              not null primary key,
    user_id    BINARY(16)                          null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
) engine = InnoDB;




