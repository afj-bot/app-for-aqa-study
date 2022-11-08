ALTER TABLE product
    MODIFY COLUMN description VARCHAR(128);

ALTER TABLE product
    MODIFY COLUMN name VARCHAR(64);

CREATE TABLE IF NOT EXISTS rating
(
    id         BINARY(16) PRIMARY KEY              NOT NULL,
    star       MEDIUMINT,
    user_id    BINARY(16)  NOT NULL,
    product_id BINARY(16)  NOT NULL,
    comment    VARCHAR(255)  NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at datetime  DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) engine = InnoDB;

ALTER TABLE rating
    ADD FOREIGN KEY (product_id) REFERENCES product (id);

CREATE index FKf813pygec41i5jo6oasdasdad
    ON rating (product_id);

