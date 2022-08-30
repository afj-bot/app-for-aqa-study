DROP TABLE order_product_ids;

CREATE TABLE IF NOT EXISTS order_product_ids
(
    order_id    BINARY(16) not null,
    product_ids BINARY(16) not null
) ENGINE = InnoDb;

CREATE INDEX FKbjreta8rasuberu69pig1ac76
    ON order_product_ids (order_id);

ALTER TABLE user
DROP COLUMN authorities;
