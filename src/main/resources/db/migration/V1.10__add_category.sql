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
    name        VARCHAR(255)                        NULL,
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

CREATE index FKf812pygec21i5jo6odudjksdjk
    ON category_localization (sub_category_id);

ALTER TABLE product
    ADD category_id BINARY(16) NOT NULL AFTER characteristic_id;

ALTER TABLE product
    ADD
        FOREIGN KEY (category_id) REFERENCES category (id);

CREATE index FKf812pygec42i5jo6odudjksdjk
    ON product (category_id);

ALTER TABLE product
    ADD sub_category_id BINARY(16) NOT NULL AFTER characteristic_id;

ALTER TABLE product
    ADD
        FOREIGN KEY (sub_category_id) REFERENCES sub_category(id);

CREATE index FKf812pygec43i5jo6odudjksdjk
    ON product (sub_category_id);

SET @CAR_ID = UUID_TO_BIN(UUID());
SET @OTHER_CAR_ID = UUID_TO_BIN(UUID());

SET @SHOES_ID = UUID_TO_BIN(UUID());
SET @OTHER_SHOES_ID = UUID_TO_BIN(UUID());

SET @WEAR_ID = UUID_TO_BIN(UUID());
SET @OTHER_WEAR_ID = UUID_TO_BIN(UUID());

SET @FLOWER_ID = UUID_TO_BIN(UUID());
SET @OTHER_FLOWER_ID = UUID_TO_BIN(UUID());

SET @DECOR_ID = UUID_TO_BIN(UUID());
SET @OTHER_DECOR_ID = UUID_TO_BIN(UUID());

SET @HOME_ID = UUID_TO_BIN(UUID());
SET @OTHER_HOME_ID = UUID_TO_BIN(UUID());

SET @BEAUTY_ID = UUID_TO_BIN(UUID());
SET @OTHER_BEAUTY_ID = UUID_TO_BIN(UUID());

SET @OTHER_ID = UUID_TO_BIN(UUID());
SET @OTHER_OTHER_ID = UUID_TO_BIN(UUID());

# Cars category
INSERT INTO category(id, name, description)
    VALUE (@CAR_ID, 'cars', 'The Cars category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_CAR_ID, 'Other', 'Other', @CAR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Автомобілі', 'Категорія автомобілів',
           @CAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Autók', 'Autók kategória',
           @CAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Cars', 'Cars category',
           @CAR_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_CAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_CAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_CAR_ID);

# Shoes category
INSERT INTO category(id, name, description)
    VALUE (@SHOES_ID, 'shoes', 'The Shoes category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_SHOES_ID, 'Other', 'Other', @SHOES_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Взуття', 'Категорія Взуття',
           @SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Cipők', 'Cipők kategória',
           @SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Shoes', 'Shoes category',
           @SHOES_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_SHOES_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_SHOES_ID);

# Wear category
INSERT INTO category(id, name, description)
    VALUE (@WEAR_ID, 'wear', 'The Wear category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_WEAR_ID, 'Other', 'Other', @WEAR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Одяг', 'Категорія одяг',
           @WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Viselet', 'Viselet kategória',
           @WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Wear', 'Wear category',
           @WEAR_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_WEAR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_WEAR_ID);

# Flower category
INSERT INTO category(id, name, description)
    VALUE (@FLOWER_ID, 'flower', 'The Flower category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_FLOWER_ID, 'Other', 'Other', @FLOWER_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Квіти', 'Категорія квіти',
           @FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Virágok', 'Virágok kategória',
           @FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Flower', 'Flower category',
           @FLOWER_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_FLOWER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_FLOWER_ID);

# Decor category
INSERT INTO category(id, name, description)
    VALUE (@DECOR_ID, 'decor', 'The Decor category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_DECOR_ID, 'Other', 'Other', @DECOR_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Декор', 'Категорія декору',
           @DECOR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Dekoráció', 'Dekoráció kategória',
           @DECOR_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Decor', 'Decor category',
           @DECOR_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_DECOR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_DECOR_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_DECOR_ID);

# Home category
INSERT INTO category(id, name, description)
    VALUE (@HOME_ID, 'home', 'The Home category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_HOME_ID, 'Other', 'Other', @HOME_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Товари для дому', 'Категорія товарів для дому',
           @HOME_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Otthoni áruk', 'Otthoni áruk kategória',
           @HOME_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Home', 'Home category',
           @HOME_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_HOME_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_HOME_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_HOME_ID);

# Beauty category
INSERT INTO category(id, name, description)
    VALUE (@BEAUTY_ID, 'beauty', 'The Beauty category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_BEAUTY_ID, 'Other', 'Other', @BEAUTY_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Товари для краси', 'Категорія товарів для краси',
           @BEAUTY_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Szépségápolási termékek', 'Szépségápolási termékek kategória',
           @BEAUTY_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Beauty', 'Beauty category',
           @BEAUTY_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_BEAUTY_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_BEAUTY_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_BEAUTY_ID);

# Other category
INSERT INTO category(id, name, description)
    VALUE (@OTHER_ID, 'other', 'The Other category');

INSERT INTO sub_category (id, name, description, category_id)
    VALUE (@OTHER_OTHER_ID, 'Other', 'Other', @OTHER_ID);

INSERT INTO category_localization (id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb kategória',
           @OTHER_ID);

INSERT INTO category_localization(id, locale, name, description, category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other category',
           @OTHER_ID);

INSERT INTO category_localization (id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'ua', 'Інше', 'Інше',
           @OTHER_OTHER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'hu', 'Egyéb', 'Egyéb',
           @OTHER_OTHER_ID);

INSERT INTO category_localization(id, locale, name, description, sub_category_id)
    VALUE (UUID_TO_BIN(UUID()), 'gb', 'Other', 'Other in the category',
           @OTHER_OTHER_ID);

UPDATE product
SET category_id = @OTHER_ID WHERE category_id IS NULL;

UPDATE product
SET sub_category_id = @OTHER_OTHER_ID WHERE category_id IS NULL;
