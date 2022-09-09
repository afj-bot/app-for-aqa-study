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
SET @OTHER_WEAR_ID = UNHEX(REPLACE('0d3d1db4-d201-465c-acca-f5c5d88c241b', '-', ''));

SET @FLOWER_ID = UNHEX(REPLACE('f550d19f-5a77-48c9-bc1f-eccb74c11c07', '-', ''));
SET @OTHER_FLOWER_ID = UNHEX(REPLACE('7767f819-0739-4da8-85dc-c1aaa9fa7675', '-', ''));

SET @DECOR_ID = UNHEX(REPLACE('cc275137-55de-4aeb-91ff-cceacfdd209a', '-', ''));
SET @OTHER_DECOR_ID = UNHEX(REPLACE('08612318-1432-405b-9c61-bc1c31ff8b6b', '-', ''));

SET @HOME_ID = UNHEX(REPLACE('59aec41c-6e9a-40b4-acde-9c4fc9f70d4c', '-', ''));
SET @OTHER_HOME_ID = UNHEX(REPLACE('f75c79f7-e63c-4e42-b71f-211285be0ae9', '-', ''));

SET @BEAUTY_ID = UNHEX(REPLACE('06d8fcfc-f5a0-4e0b-8429-b835ec49774e', '-', ''));
SET @OTHER_BEAUTY_ID = UNHEX(REPLACE('9e67d4e2-acd0-4305-b7dd-2130449755f2', '-', ''));

INSERT INTO category(id, name, description)
    VALUE (@CAR_ID, 'Cars', 'The cars category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_CAR_ID, 'Other', 'Other', @CAR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('7b7a3aa5-d065-46d9-9d79-939f4ed3d68d', '-', '')), 'ua', 'Автомобілі', 'Категорія автомобілів',
           @CAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('ae68d3ec-3280-42f9-9716-9137df80e0e5', '-', '')), 'hu', 'Autók', 'Autók kategória',
           @CAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('3ebac864-6646-4108-97f3-d3528d8adaf3', '-', '')), 'gb', 'Cars', 'Cars category',
           @CAR_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('6ced274f-8d7f-4a9a-a38f-d9186174cafc', '-', '')), 'ua', 'Інше', 'Інше',
           @OTHER_CAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('c9388a5d-27d8-432e-856f-7ef1736748d1', '-', '')), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_CAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('814b1611-c394-434b-a6cc-038632cdc4d5', '-', '')), 'gb', 'Other', 'Other in the category',
           @OTHER_CAR_ID);

INSERT INTO category(id, name, description)
    VALUE (@SHOES_ID, 'Shoes', 'The shoes category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_SHOES_ID, 'Other', 'Other', @SHOES_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('afab247b-7591-49a7-95ae-95081629b261', '-', '')), 'ua', 'Взуття', 'Категорія Взуття',
           @SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('d798f1fb-5a09-40bf-b376-1b7a89b61673', '-', '')), 'hu', 'Cipők', 'Cipők kategória',
           @SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('38f534d4-8f47-44bf-aec5-89ca1f2da5df', '-', '')), 'gb', 'Shoes', 'Shoes category',
           @SHOES_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('84aaf15d-8d84-4628-9a85-fa5dc7efcdfe', '-', '')), 'ua', 'Інше', 'Інше',
           @OTHER_SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('cb6cd42c-05d6-42cb-8c34-cf840ccfd599', '-', '')), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('3abe172b-a21e-4195-a4b3-e4eeffd73f2b', '-', '')), 'gb', 'Other', 'Other in the category',
           @OTHER_SHOES_ID);

INSERT INTO category(id, name, description)
    VALUE (@WEAR_ID, 'Wear', 'The Wear category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_WEAR_ID, 'Other', 'Other', @WEAR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('01a7efdc-8ef4-481e-866d-2158a155afb7', '-', '')), 'ua', 'Одяг', 'Категорія одяг',
           @WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('680fcb06-d10a-4844-8e8a-480be6e48272', '-', '')), 'hu', 'Viselet', 'Viselet kategória',
           @WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('858d9295-e167-4dd4-bc77-8221dcad6b67', '-', '')), 'gb', 'Wear', 'Wear category',
           @WEAR_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('f7419dd8-9c5f-4498-b953-1a3f3ce5187f', '-', '')), 'ua', 'Інше', 'Інше',
           @OTHER_WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('ac47c32f-39f1-4b0d-b946-f249f5859b14', '-', '')), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('a1192690-db3e-4942-84ea-5e1fae5c68dc', '-', '')), 'gb', 'Other', 'Other in the category',
           @OTHER_WEAR_ID);

INSERT INTO category(id, name, description)
    VALUE (@FLOWER_ID, 'Flower', 'The Flower category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_FLOWER_ID, 'Other', 'Other', @FLOWER_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('2a30de3b-771f-4d9e-975f-8505a01dff87', '-', '')), 'ua', 'Квіти', 'Категорія квіти',
           @FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('0c7ddab1-065b-4207-bab8-2973ca1a21d6', '-', '')), 'hu', 'Virágok', 'Virágok kategória',
           @FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('959a38ec-dba9-4f50-854e-d12bd1c4a6e7', '-', '')), 'gb', 'Flower', 'Flower category',
           @FLOWER_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('efaa8f6c-8838-4c1e-8ba7-5b3ca3a74e93', '-', '')), 'ua', 'Інше', 'Інше',
           @OTHER_FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('1ba7d830-e386-4bd0-97d7-ca2511019115', '-', '')), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('fa111bb9-fff2-483f-b376-eb191bd99b4b', '-', '')), 'gb', 'Other', 'Other in the category',
           @OTHER_FLOWER_ID);

INSERT INTO category(id, name, description)
    VALUE (@DECOR_ID, 'Flower', 'The Flower category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_DECOR_ID, 'Other', 'Other', @DECOR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('2a30de3b-771f-4d9e-975f-8505a01dff87', '-', '')), 'ua', 'Квіти', 'Категорія квіти',
           @FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('0c7ddab1-065b-4207-bab8-2973ca1a21d6', '-', '')), 'hu', 'Virágok', 'Virágok kategória',
           @FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UNHEX(REPLACE('959a38ec-dba9-4f50-854e-d12bd1c4a6e7', '-', '')), 'gb', 'Flower', 'Flower category',
           @FLOWER_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('efaa8f6c-8838-4c1e-8ba7-5b3ca3a74e93', '-', '')), 'ua', 'Інше', 'Інше',
           @OTHER_FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('1ba7d830-e386-4bd0-97d7-ca2511019115', '-', '')), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UNHEX(REPLACE('fa111bb9-fff2-483f-b376-eb191bd99b4b', '-', '')), 'gb', 'Other', 'Other in the category',
           @OTHER_FLOWER_ID);