USE app;
INSERT INTO user(id, username, email, password,
                 first_name, last_name, phone_number,
                 home_address, privacy_policy, account_non_expired, account_non_locked,
                 credentials_non_expired, enabled)
VALUES (UNHEX(REPLACE('3b0a4223-35e6-47b1-9ac3-f95911979574', '-','')),
        'unit_test',
        'unit_test@mailinator.com',
        '$2a$10$QX1zQVtAoksD3KW2VjrhNux4b1I43FS.QIMJ1ttPb2TKDD./xPMY.',
        'Unit',
        'Test',
        '380987100379',
        'test',
        1,
        1,
        1,
        1,
        1);

INSERT INTO characteristic(id, size, color, additional_params)
VALUES (UNHEX(REPLACE('252c63e6-6ca1-42d4-80cb-0b2dd2e1002f', '-', '')),
        'S',
        'blue',
        'test');

INSERT INTO image(id, file_name, picture)
VALUES (UNHEX(REPLACE('d0a5b667-8419-458e-90f2-255dfd39be9a', '-', '')),
        'Screenshot 2022-07-27 at 15.16.47.png',
        '754x1046 PNG image 158.64 kB');

INSERT INTO product(id, name, price, description, image_id, characteristic_id, created_user_id, currency, quantity, category_id, sub_category_id)
VALUES  (UNHEX(REPLACE('2a51b256-a6ef-4748-9354-a869290c3bf0', '-', '')),
         'test',
         24.4,
         'test_product',
         UNHEX(REPLACE('d0a5b667-8419-458e-90f2-255dfd39be9a', '-', '')),
         UNHEX(REPLACE('252c63e6-6ca1-42d4-80cb-0b2dd2e1002f', '-', '')),
         UNHEX(REPLACE('3b0a4223-35e6-47b1-9ac3-f95911979574', '-', '')),
         'USD',
         20,
         (SELECT id FROM category WHERE category.name = 'other'),
         (SELECT id FROM sub_category WHERE category_id = (SELECT id FROM category WHERE category.name = 'other') AND sub_category.name = 'other'));


INSERT INTO user_order(id, status, total, user_id)
VALUES (UNHEX(REPLACE('78452df2-1e97-4c0e-a691-9b381c714850', '-', '')),
        'IN_PROGRESS',
        24.4,
        UNHEX(REPLACE('3b0a4223-35e6-47b1-9ac3-f95911979574', '-', '')));
INSERT INTO order_product_ids(order_id, product_ids)
VALUES (UNHEX(REPLACE('78452df2-1e97-4c0e-a691-9b381c714850', '-', '')),
        UNHEX(REPLACE('2a51b256-a6ef-4748-9354-a869290c3bf0', '-', '')));

INSERT INTO user_order(id, status, total, user_id)
VALUES (UNHEX(REPLACE('3e5451f2-efb3-42ce-88f1-1a566421e676', '-', '')),
        'CANCEL',
        24.4,
        UNHEX(REPLACE('3b0a4223-35e6-47b1-9ac3-f95911979574', '-', '')));
INSERT INTO order_product_ids(order_id, product_ids)
VALUES (UNHEX(REPLACE('3e5451f2-efb3-42ce-88f1-1a566421e676', '-', '')),
        UNHEX(REPLACE('2a51b256-a6ef-4748-9354-a869290c3bf0', '-', '')));

INSERT INTO user_order(id, status, total, user_id)
VALUES (UNHEX(REPLACE('6434e8d9-3ab1-40e0-88c2-bc318538f8e1', '-', '')),
        'WAITING_FOR_PAYMENT',
        24.4,
        UNHEX(REPLACE('3b0a4223-35e6-47b1-9ac3-f95911979574', '-', '')));
INSERT INTO order_product_ids(order_id, product_ids)
VALUES (UNHEX(REPLACE('6434e8d9-3ab1-40e0-88c2-bc318538f8e1', '-', '')),
        UNHEX(REPLACE('2a51b256-a6ef-4748-9354-a869290c3bf0', '-', '')));