DROP DATABASE IF EXISTS sql_shoe_webshop;
CREATE DATABASE sql_shoe_webshop;
USE sql_shoe_webshop;
SET sql_safe_updates = 0;
SET GLOBAL log_bin_trust_function_creators = 1;

-- DONE!

CREATE TABLE customer (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number INT,
    email VARCHAR(50),
    password VARCHAR(50),
    social_security_number VARCHAR(10),
    address VARCHAR(50),
    fk_city_id INT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE INDEX IX_customer_Last_name ON customer (last_name);
-- CREATE INDEX IX_customer_full_name ON customer (first_name ,last_name);
-- Man vill kunna söker ofta på förnamn och efternamn tillsammans



CREATE TABLE city (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(30),
    zip_code INT,
    UNIQUE (city_name),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_name varchar(30),
    price_sek DOUBLE,
    -- valt att göra valutaomvandligen i backend-programmet istället
    stock INT default 5,
    fk_color_id INT,
    fk_size_id INT,
    fk_brand_id INT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE color (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    color VARCHAR(30),
    UNIQUE(color),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE category (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50),
    UNIQUE(category_name),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE size (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    eu DOUBLE,
    uk DOUBLE,
    us DOUBLE,
    cm DOUBLE,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE brand (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(50),
    UNIQUE (brand_name),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product_category (
    id INT NOT NULL AUTO_INCREMENT  PRIMARY KEY ,
    fk_product_id INT,
    fk_category_id INT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id INT NOT NULL AUTO_INCREMENT  PRIMARY KEY ,
    date DATE,
    time TIME,
    fk_customer_id INT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders_product (
    id INT NOT NULL AUTO_INCREMENT  PRIMARY KEY ,
    fk_orders_id INT,
    fk_product_id INT,
    product_price DOUBLE,
    quantity INT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product_review (
    id INT NOT NULL AUTO_INCREMENT  PRIMARY KEY ,
    fk_product_id int,
    fk_customer_id int,
    fk_rating_id int,
    review varchar(16000),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE rating (
    id INT NOT NULL AUTO_INCREMENT  PRIMARY KEY ,
    rating_text varchar(15),
	rating_number int,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- add all foreign keys

ALTER TABLE customer
ADD FOREIGN KEY(fk_city_id) REFERENCES city(id);

ALTER TABLE product
ADD FOREIGN KEY(fk_size_id) REFERENCES size(id),
ADD FOREIGN KEY(fk_brand_id) REFERENCES brand(id) ON DELETE CASCADE,
ADD FOREIGN KEY(fk_color_id) REFERENCES color(id);

-- nr vi tar bort ett brand så tar vi bort alla producter som ingår i de märket också
-- för vi vill inte ha några brandfria skor i våran shop

ALTER TABLE product_category
ADD FOREIGN KEY (fk_product_id) REFERENCES product(id) ON DELETE SET NULL,
ADD FOREIGN KEY (fk_category_id) REFERENCES category(id);

ALTER TABLE orders
ADD FOREIGN KEY (fk_customer_id) REFERENCES customer(id) ON DELETE SET NULL;

ALTER TABLE orders_product
ADD FOREIGN KEY (fk_orders_id) REFERENCES orders(id),
ADD FOREIGN KEY (fk_product_id) REFERENCES product(id) ON DELETE SET NULL;

ALTER TABLE product_review
ADD FOREIGN KEY (fk_product_id) REFERENCES product(id) ON DELETE CASCADE,
ADD FOREIGN KEY (fk_customer_id) REFERENCES customer(id) ON DELETE SET NULL,
ADD FOREIGN KEY (fk_rating_id) REFERENCES rating(id);