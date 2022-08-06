ALTER TABLE product
    ADD created_user_id BINARY(16) NOT NULL AFTER characteristic_id;

ALTER TABLE product
    ADD FOREIGN KEY (created_user_id) REFERENCES user (id);

CREATE index FKf812pygec41i5jo6oasdasdad
    ON product (created_user_id);

