USE sql_shoe_webshop;

INSERT INTO city (city_name) VALUES
('stockholm'),
('funäsdalen'),
('luleå'),
('gävle'),
('uppsala'),
('rättvik');

INSERT INTO customer (first_name, last_name, phone_number, email, password, social_security_number, address, fk_city_id) VALUES
('oscar','forss', '0703455445', 'oscar@email.com',AES_ENCRYPT('1234',UNHEX(SHA2('key',512))), '9014321234', 'sliepnervägen', (SELECT id FROM city WHERE city_name = 'stockholm')),
('patrik','melander', '0703236545', 'patrik@email.com',AES_ENCRYPT('1234',UNHEX(SHA2('key',512))), '8709421234', 'bromma', (SELECT id FROM city WHERE city_name = 'stockholm')),
('cribb','grännby', '0703876513', 'cribb@email.com',AES_ENCRYPT('1234',UNHEX(SHA2('key',512))), '8509221234', 'blackeberg', (SELECT id FROM city WHERE city_name = 'funäsdalen')),
('stefan','henning', '0703233314', 'stefan@email.com',AES_ENCRYPT('1234',UNHEX(SHA2('key',512))), '8210221234', 'vikingavägen', (SELECT id FROM city WHERE city_name = 'gävle')),
('julia','wingsteadts', '0732344549', 'julia@email.com',AES_ENCRYPT('1234',UNHEX(SHA2('key',512))), '9603321234', 'huddingevägen', (SELECT id FROM city WHERE city_name = 'stockholm')),
('sigrun','dottir', '0703231945', 'sigrun@email.com',AES_ENCRYPT('1234',UNHEX(SHA2('key',512))), '8106201234', 'älvsjövägen', (SELECT id FROM city WHERE city_name = 'rättvik'));

-- to decode the password
SELECT password,cast(aes_decrypt(password,UNHEX(SHA2('key',512))) AS CHAR(100)) FROM sql_shoe_webshop.customer;

INSERT INTO color (color) VALUES
('svart'),
('brun'),
('beige'),
('grå'),
('vit'),
('blå'),
('grön'),
('gul'),
('orange'),
('röd'),
('rosa'),
('lila'),
('flerfärgad');

INSERT INTO size (eu, uk, us, cm) VALUES
('32','0.5','1','20'),
('33','1','1.5','20.5'),
('34','2','2.5','21.5'),
('35','2.5','3','22'),
('36','4','4.5','22.6'),
('37','4.5','5','23.2'),
('38','5.5','6','23.9'),
('39','6.5','7','24.5'),
('40','7','7.5','25.2'),
('41','7.5','8','25.9'),
('42','8','8.5','26.5'),
('43','8.5','9','27.2'),
('44','10','10.5','27.9'),
('45','11','11.5','28.5'),
('46','12','12.5','29.2'),
('47','13','13.5','29.8');

INSERT INTO brand (brand_name) VALUES
('adidas'),
('nike'),
('armani'),
('reebok'),
('puma'),
('lacoste'),
('new balance'),
('ecco'),
('dc'),
('mishael kors');

INSERT INTO product (product_name, price_sek, fk_color_id, fk_size_id, fk_brand_id) VALUES
('flex runner', '399', '1','1','2'),-- 1
('flex runner', '399', '1','2','2'),
('flex runner', '399', '1','3','2'),
('flex runner', '399', '1','4','2'),
('flex runner', '399', '1','5','2'),

('solar boost', '1699', '12','5','1'), -- 6
('solar boost', '1699', '12','6','1'),
('solar boost', '1699', '12','7','1'),
('solar boost', '1699', '12','8','1'),
('solar boost', '1699', '12','9','1'),
('solar boost', '1699', '12','10','1'),

('ultra boost', '1999', '3','10','1'), -- 12
('ultra boost', '1999', '3','11','1'),
('ultra boost', '1999', '3','12','1'),
('ultra boost', '1999', '3','13','1'),
('ultra boost', '1999', '3','14','1'),
('ultra boost', '1999', '3','15','1'),
('ultra boost', '1999', '3','16','1'),

('pegasus', '1299', '1','6','2'), -- 19
('pegasus', '1299', '1','7','2'),
('pegasus', '1299', '1','8','2'),
('pegasus', '1299', '1','9','2'),
('pegasus', '1299', '1','10','2'),
('pegasus', '1299', '1','11','2'),
('pegasus', '1299', '1','12','2'),

('Vomero', '1499', '1','10','2'), -- 26
('Vomero', '1499', '1','11','2'),
('Vomero', '1499', '1','12','2'),
('Vomero', '1499', '1','13','2'),
('Vomero', '1499', '1','14','2'),
('Vomero', '1499', '1','15','2'),
('Vomero', '1499', '1','16','2'),

('offroad','799','1','5','8'), -- 33
('offroad','799','1','6','8'),
('offroad','799','1','7','8'),
('offroad','799','1','8','8'),
('offroad','799','1','9','8'),

('lace-up','3499','2','10','3'), -- 38
('lace-up','3499','2','11','3'),
('lace-up','3499','2','12','3'),
('lace-up','3499','2','13','3'),
('lace-up','3499','2','14','3'),
('lace-up','3499','2','15','3'),
('lace-up','3499','2','16','3'),

('lace-up','3499','1','10','3'), -- 45
('lace-up','3499','1','11','3'),
('lace-up','3499','1','12','3'),
('lace-up','3499','1','13','3'),
('lace-up','3499','1','14','3'),
('lace-up','3499','1','15','3'),
('lace-up','3499','1','16','3'),

('keke','1500','1','6','10'), -- 52
('keke','1500','1','7','10'),
('keke','1500','1','8','10'),
('keke','1500','1','9','10'),
('keke','1500','1','10','10'),

('air max fusion', '549', '5','1','2'), -- 57
('air max fusion', '549', '5','2','2'),
('air max fusion', '549', '5','3','2'),
('air max fusion', '549', '5','4','2'),
('air max fusion', '549', '5','5','2'),

('questar', '799', '1','5','1'), -- 62
('questar', '799', '1','6','1'),
('questar', '799', '1','7','1'),
('questar', '799', '1','8','1'),
('questar', '799', '1','9','1'),

('kaptir','849','5','6','1'), -- 67
('kaptir','849','5','7','1'),
('kaptir','849','5','8','1'),
('kaptir','849','5','9','1'),
('kaptir','849','5','10','1'),

('tanjun','749','5','10','2'), -- 72
('tanjun','749','5','11','2'),
('tanjun','749','5','12','2'),
('tanjun','749','5','13','2'),
('tanjun','749','5','14','2'),
('tanjun','749','5','15','2');




INSERT INTO category (category_name) VALUES
('löparsko'),
('sandal'),
('klacksko'),
('sneaker'),
('barn'),
('herr'),
('dam'),
('finsko');

INSERT INTO product_category(fk_product_id, fk_category_id) VALUES
(1,1),(2,1),(3,1),(4,1),(5,1),
(1,5),(2,5),(3,5),(4,5),(5,5),

(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),
(6,7),(7,7),(8,7),(9,7),(10,7),(11,7),

(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),
(12,6),(13,6),(14,6),(15,6),(16,6),(17,6),(18,6),

(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),
(19,7),(20,7),(21,7),(22,7),(23,7),(24,7),(25,7),

(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),
(26,6),(27,6),(28,6),(29,6),(30,6),(31,6),(32,6),

(33,2),(34,2),(35,2),(36,2),(37,2),
(33,7),(34,7),(35,7),(36,7),(37,7),

(38,8),(39,8),(40,8),(41,8),(42,8),(43,8),(44,8),
(38,6),(39,6),(40,6),(41,6),(42,6),(43,6),(44,6),

(45,8),(46,8),(47,8),(48,8),(49,8),(50,8),(51,8),
(45,6),(46,6),(47,6),(48,6),(49,6),(50,6),(51,6),

(52,3),(53,3),(54,3),(55,3),(56,3),
(52,7),(53,7),(54,7),(55,7),(56,7),

(57,4),(58,4),(59,4),(60,4),(61,4),
(57,5),(58,5),(59,5),(60,5),(61,5),

(62,4),(63,4),(64,4),(65,4),(66,4),
(62,7),(63,7),(64,7),(65,7),(66,7),

(67,4),(68,4),(69,4),(70,4),(71,4),
(67,7),(68,7),(69,7),(70,7),(71,7),

(72,4),(73,4),(74,4),(75,4),(76,4),(77,4),
(72,6),(73,6),(74,6),(75,6),(76,6),(77,6);

INSERT INTO orders (id, date, time, fk_customer_id) VALUES (1,'2020-01-13','17:30', (SELECT id FROM customer WHERE first_name = 'oscar'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (2,'2020-02-12','17:30', (SELECT id FROM customer WHERE first_name = 'oscar'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (3,'2020-01-13','17:30', (SELECT id FROM customer WHERE first_name = 'patrik'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (4,'2020-03-13','17:30', (SELECT id FROM customer WHERE first_name = 'patrik'));

INSERT INTO orders (id, date, time, fk_customer_id) VALUES (5,'2020-01-13','17:30', (SELECT id FROM customer WHERE first_name = 'cribb'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (6,'2020-03-13','17:30', (SELECT id FROM customer WHERE first_name = 'cribb'));

INSERT INTO orders (id, date, time, fk_customer_id) VALUES (7, '2019-07-13','17:30', (SELECT id FROM customer WHERE first_name = 'stefan'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (8, '2021-02-01','17:30', (SELECT id FROM customer WHERE first_name = 'stefan'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (9, '2020-06-13','17:30', (SELECT id FROM customer WHERE first_name = 'julia'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (10, '2021-01-13','17:30', (SELECT id FROM customer WHERE first_name = 'julia'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (11, '2020-08-16','17:30', (SELECT id FROM customer WHERE first_name = 'sigrun'));
INSERT INTO orders (id, date, time, fk_customer_id) VALUES (12, '2018-01-13','17:30', (SELECT id FROM customer WHERE first_name = 'sigrun'));

INSERT INTO orders_product(fk_orders_id, fk_product_id, product_price, quantity) VALUES (1,2, (SELECT price_sek FROM product where id =1), 2),(1,5, (SELECT price_sek FROM product where id =1), 2),(1,12, (SELECT price_sek FROM product where id =12), 2);
INSERT INTO orders_product(fk_orders_id, fk_product_id, product_price, quantity) VALUES (2,35, (SELECT price_sek FROM product where id =35), 1),(2,3, (SELECT price_sek FROM product where id =3), 2),(2,15, (SELECT price_sek FROM product where id =15), 2);
INSERT INTO orders_product(fk_orders_id, fk_product_id, product_price, quantity) VALUES (3,35, (SELECT price_sek FROM product where id =35), 1),(3,5, (SELECT price_sek FROM product where id =5), 2),(3,12, (SELECT price_sek FROM product where id =12), 2);
INSERT INTO orders_product(fk_orders_id, fk_product_id, product_price, quantity) VALUES (4,1, (SELECT price_sek FROM product where id =1), 2),(4,1, (SELECT price_sek FROM product where id =1), 2),(4,1, (SELECT price_sek FROM product where id =1), 2);
INSERT INTO orders_product(fk_orders_id, fk_product_id, product_price, quantity) VALUES (5,2, (SELECT price_sek FROM product where id =2), 2),(5,7, (SELECT price_sek FROM product where id =7), 2),(5,17, (SELECT price_sek FROM product where id =17), 2);
INSERT INTO orders_product(fk_orders_id, fk_product_id, product_price, quantity) VALUES (6,11, (SELECT price_sek FROM product where id =11), 2),(6,13, (SELECT price_sek FROM product where id =13), 2),(6,12, (SELECT price_sek FROM product where id =12), 2);
INSERT INTO orders_product (fk_orders_id, fk_product_id, product_price, quantity) values (7,1, (select price_sek from product where id = 1), 2 ),(7,2, (select price_sek from product where id = 2), 1 );
INSERT INTO orders_product (fk_orders_id, fk_product_id, product_price, quantity) values (8,4, (select price_sek from product where id = 4), 5 ),(8,8, (select price_sek from product where id = 8), 2 );
INSERT INTO orders_product (fk_orders_id, fk_product_id, product_price, quantity) values (9,5, (select price_sek from product where id = 5), 6 ),(9,9, (select price_sek from product where id = 9), 1 );
INSERT INTO orders_product (fk_orders_id, fk_product_id, product_price, quantity) values (10,10, (select price_sek from product where id = 10), 2 ),(10,13, (select price_sek from product where id = 13), 3 );
INSERT INTO orders_product (fk_orders_id, fk_product_id, product_price, quantity) values (11,1, (select price_sek from product where id = 1), 1);
INSERT INTO orders_product (fk_orders_id, fk_product_id, product_price, quantity) values (12,57, (select price_sek from product where id = 57), 1);

INSERT INTO rating (rating_text, rating_number) VALUES
('missnöjd',1),
('inte nöjd',2),
('ganska nöjd',3),
('nöjd',4),
('mycket nöjd',5);

INSERT INTO product_review (fk_product_id,fk_customer_id,fk_rating_id,review) VALUES
(4,1,4,'Väldigt nöjd med kvalitet samt storlek'),
(7,1,3,'Bra produkt till bra pris'),
(8,1,4,'Fint sko, underbar färg'),
(74,1,4,'Underbar att gå i'),
(54,1,3,'mycket fin utformad sko'),
(4,2,2,'helt ok sko'),
(8,2,1,'gräslig färg och "fitt" på foten'),
(54,2,1,'tråkig sko, värdelös i färgen'),
(18,2,4,'bästa köpet på hemsidan!'),
(14,2,2,'ok, inget speciellt med denna sko'),
(14,3,1,'Sämst jag någonsin köpt, inte värt priset!'),
(6,3,2,'Ok, vara, storlek stämmmer bra'),
(18,5,4,'Bra produkt!'),
(22,6,3,'Fint sko, älskar färgen'),
(24,6,2,'ok, kvalitet. Fin färg');
