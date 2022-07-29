ALTER TABLE product
    ADD quantity MEDIUMINT AFTER currency;

UPDATE product
SET quantity = 1;

