ALTER TABLE product
    ADD currency varchar(255) AFTER image_id;
ALTER TABLE product
    ADD description varchar(255) AFTER price;

UPDATE product
SET currency = 'USD';

ALTER TABLE product
    DROP INDEX name;
