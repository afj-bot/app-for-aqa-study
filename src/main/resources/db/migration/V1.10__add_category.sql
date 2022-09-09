CREATE TABLE IF NOT EXISTS category
(
    id          BINARY(16)                          NOT NULL PRIMARY KEY,
    name        VARCHAR(255)                        NULL UNIQUE,
    description VARCHAR(255)                        NULL,
    created_at  timestamp default CURRENT_TIMESTAMP NULL,
    updated_at  datetime  default CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS sub_category
(
    id          BINARY(16) PRIMARY KEY              NOT NULL PRIMARY KEY,
    name        VARCHAR(255)                        NULL UNIQUE,
    description VARCHAR(255)                        NULL,
    category_id BINARY(16)                          NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    created_at  timestamp default CURRENT_TIMESTAMP NULL,
    updated_at  datetime  default CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS category_localization
(
    id              BINARY(16) PRIMARY KEY              NOT NULL PRIMARY KEY,
    locale          VARCHAR(255)                        NULL,
    name            VARCHAR(255)                        NULL,
    description     VARCHAR(255)                        NULL,
    category_id     BINARY(16)                          NULL,
    sub_category_id BINARY(16)                          NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (sub_category_id) REFERENCES sub_category (id),
    created_at      timestamp default CURRENT_TIMESTAMP NULL,
    updated_at      datetime  default CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) engine = InnoDB;

CREATE index FKf812pygec23i5jo6odudjksdjk
    ON sub_category (category_id);

CREATE index FKf812pygec22i5jo6odudjksdjk
    ON category_localization (category_id);

CREATE index FKf812pygec23i5jo6odudjksdjk
    ON category_localization (sub_category_id);

SET @CAR_ID = UNHEX(REPLACE('6d0d4d8d-0d7e-4e68-9fbb-6cecc5009e36', '-', ''));
SET @OTHER_CAR_ID = UNHEX(REPLACE('73586065-5fdf-4ee0-808e-a561853c7972', '-', ''));
SET @SHOES_ID = UNHEX(REPLACE('82920eb9-a1d4-492b-902c-db0801bae7eb', '-', ''));
SET @OTHER_SHOES_ID = UNHEX(REPLACE('20a0f352-36ad-49da-8b4d-757b49f33a22', '-', ''));
SET @WEAR_ID = UNHEX(REPLACE('6562e878-78b9-4e4c-8b64-1384ebba1461', '-', ''));
SET @FLOWER_ID = UNHEX(REPLACE('f550d19f-5a77-48c9-bc1f-eccb74c11c07', '-', ''));
SET @DECOR_ID = UNHEX(REPLACE('cc275137-55de-4aeb-91ff-cceacfdd209a', '-', ''));
SET @HOME_ID = UNHEX(REPLACE('59aec41c-6e9a-40b4-acde-9c4fc9f70d4c', '-', ''));
SET @BEAUTY_ID = UNHEX(REPLACE('06d8fcfc-f5a0-4e0b-8429-b835ec49774e', '-', ''));

INSERT INTO category(id, name, description)
    VALUE (@CAR_ID, 'Cars', 'The cars category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (UNHEX(REPLACE('73586065-5fdf-4ee0-808e-a561853c7972', '-', '')), 'Other', 'Other',
           @CAR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('7b7a3aa5-d065-46d9-9d79-939f4ed3d68d', '-', '')), 'ua', 'Автомобілі', 'Категорія автомобілів',
           @CAR_ID);