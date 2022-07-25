ALTER TABLE user_order
    ADD status varchar(255) AFTER id;
ALTER TABLE user_order
    ADD total numeric(8, 2) AFTER status;
ALTER TABLE user_order
    ADD product_ids varchar(255) AFTER total;

UPDATE user_order
SET status = 'DONE';

UPDATE user_order
SET total = 00.00;

CREATE TABLE IF NOT EXISTS order_product_ids
(
    order_id    binary(255) not null,
    product_ids binary(255) null
)
    engine = InnoDb;

CREATE INDEX FKbjreta8rasuberu69pig1ac75
    ON order_product_ids (order_id);


ALTER TABLE user_order
    DROP FOREIGN KEY user_order_ibfk_1;

DROP INDEX FKf812pygec41i5jo6oduhrr022 ON user_order;




