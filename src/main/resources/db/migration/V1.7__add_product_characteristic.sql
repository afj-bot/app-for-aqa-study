CREATE TABLE IF NOT EXISTS characteristic
(
    id                BINARY(16) PRIMARY KEY              not null primary key,
    size              varchar(255)                        null,
    color             varchar(255)                        null,
    additional_params varchar(255)                        null,
    created_at        timestamp default CURRENT_TIMESTAMP null,
    updated_at        datetime  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
) engine = InnoDB;

ALTER TABLE product
    ADD characteristic_id BINARY(16) AFTER image_id;
ALTER TABLE product
    ADD
        FOREIGN KEY (characteristic_id) REFERENCES characteristic (id);

CREATE index FKf812pygec41i5jo6odudjksdjk
    ON product (characteristic_id);