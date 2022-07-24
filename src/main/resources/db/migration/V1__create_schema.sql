CREATE TABLE IF NOT EXISTS user
(
    id                      BINARY(16)                          not null primary key,
    username                varchar(255)                        null unique,
    email                   varchar(255)                        null unique,
    password                varchar(255)                        null,
    first_name              varchar(255)                        null,
    last_name               varchar(255)                        null,
    phone_number            varchar(255)                        null,
    home_address            varchar(255)                        null,
    date_of_birth           datetime                            null,
    account_non_expired     TINYINT(1),
    account_non_locked      TINYINT(1),
    credentials_non_expired TINYINT(1),
    enabled                 TINYINT(1),
    created_at              timestamp default CURRENT_TIMESTAMP null,
    updated_at              datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS image
(
    id         BINARY(16) PRIMARY KEY              not null primary key,
    file_name  varchar(255),
    picture    LONGBLOB,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product
(
    id         BINARY(16)                          not null primary key,
    name       varchar(255)                        null unique,
    price      numeric(8, 2)                       null,
    image_id   BINARY(16)                          null,
    FOREIGN KEY (image_id) REFERENCES image (id),
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS user_order
(
    id         BINARY(16)                          not null primary key,
    user_id    BINARY(16)                          not null,
    product_id BINARY(16)                          not null,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP

) engine = InnoDb;

CREATE index FKf812pygec41i5jo6oduhrr0aw
    ON user_order (user_id);

CREATE index FKf812pygec41i5jo6oduhrr022
    ON user_order (product_id);

CREATE index FKf812pygec41i5jo6oduhrr012
    ON product (image_id);