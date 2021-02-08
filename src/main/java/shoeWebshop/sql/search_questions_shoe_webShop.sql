USE sql_shoe_webshop;

-- Visa index IX_customer_full_name används för att söka på förnamn och efternamn tillsammans
-- DONE INGA BUGGAR
SELECT DISTINCT
    TABLE_NAME,
    INDEX_NAME
FROM INFORMATION_SCHEMA.STATISTICS
WHERE TABLE_SCHEMA = 'sql_shoe_webshop';

-- Lista antalet produkter per kategori. Listningen ska innehålla kategori-namn och antalet produkter.
-- DONE INGA BUGGAR
SELECT category.category_name as Categori, COUNT(category.id) as Antal
FROM category
         INNER JOIN product_category
                    ON category.id = product_category.fk_category_id
GROUP BY category.category_name;

-- Skapa en kundlista med den totala summan pengar som varje kund har handlat för.
-- Kundens för- och efternamn, samt det totala värdet som varje person har shoppats för, skall visas.
-- DONE INGA BUGGAR
SELECT
    customer.first_name Namn,
    customer.last_name Efternamn,
    SUM(orders_product.product_price * orders_product.quantity) Beställningsvärdet
FROM customer
         JOIN orders
              ON customer.id = orders.fk_customer_id
         JOIN orders_product
              ON orders.id = orders_product.fk_orders_id
GROUP BY customer.first_name
ORDER BY Beställningsvärdet DESC;

-- Vilka kunder har köpt svarta sandaler i storlek 38 av märket Ecco? Lista deras namn
-- DONE INGA BUGGAR
SELECT
    customer.first_name Namn,
    customer.last_name Efternamn,
    product.product_name Name,
    category.category_name Categori,
    color.color Färg,
    brand.brand_name Märke,
    size.eu
FROM customer
         JOIN orders
              ON customer.id = orders.fk_customer_id
         JOIN orders_product
              ON orders.id = orders_product.fk_orders_id
         JOIN product
              ON orders_product.fk_product_id = product.id
         JOIN color
              ON product.fk_color_id = color.id
         JOIN brand
              ON product.fk_brand_id = brand.id
         JOIN size
              ON product.fk_size_id = size.id
         JOIN product_category
              ON product_category.fk_product_id = product.id
         JOIN category
              ON category.id = product_category.fk_category_id
WHERE
        fk_color_id = (SELECT id FROM color WHERE color = 'svart' ) AND fk_size_id = (SELECT id FROM size WHERE eu = 38 )
  AND fk_brand_id = (SELECT id FROM brand WHERE brand_name = 'ecco' ) AND fk_category_id = (SELECT id FROM category WHERE category_name ='sandal')
GROUP BY customer.last_name;


-- Skriv ut en lista på det totala beställningsvärdet per ort där beställningsvärdet är större än
-- 1000 kr. Ortnamn och värde ska visas. (det måste finnas orter i databasen där det har
-- handlats för mindre än 1000 kr för att visa att frågan är korrekt formulerad)
-- Sigrun enda som bor i dalarn och handlat för 750 kr
-- DONE INGA BUGGAR
SELECT
    city.city_name Ortnamn,
    SUM(orders_product.product_price * orders_product.quantity) totala_beställningsvärdet
FROM city
         JOIN customer
              ON city.id = customer.fk_city_id
         JOIN orders
              ON customer.id = orders.fk_customer_id
         JOIN orders_product
              ON orders.id = orders_product.fk_orders_id
GROUP BY city_name;
-- HAVING totala_beställningsvärdet > 1000;

-- Skapa en topp-5 lista av de mest sålda produkterna
-- DONE INGA BUGGAR
SELECT
    product.id ID,
    product.product_name Namn,
    size.eu storlek,
    sum(orders_product.quantity) antal_sålda
FROM product
         JOIN orders_product
              ON product.id = orders_product.fk_product_id
         JOIN size
              ON product.fk_size_id = size.id
group by ID
ORDER BY antal_sålda DESC
LIMIT 5;

-- Vilken månad hade du den största försäljningen? (det måste finnas data som anger
-- försäljning för mer än en månad i databasen för att visa att frågan är korrekt formulerad)
-- DONE INGA BUGGAR
SELECT
    MONTHNAME(orders.date) Månad,
    YEAR(orders.date) År,
    SUM(orders_product.product_price * orders_product.quantity) 'Sålt för'
FROM orders
         JOIN orders_product
              ON orders.id = orders_product.fk_orders_id
GROUP BY MONTHNAME(orders.date), MONTH(orders.date), YEAR(orders.date)
ORDER BY sum(orders_product.product_price * orders_product.quantity) DESC;
-- LIMIT 1;

-- Visa omdömmen
SELECT
    product.product_name,
    rating.rating_text,
    rating.rating_number,
    product_review.review Review
FROM product_review
         JOIN product
              ON product_review.fk_product_id = product.id
         JOIN rating
              ON product_review.fk_rating_id = rating.id
ORDER BY rating_number DESC;

-- Refererns-integritet
-- När vi tar bort en kund så försvinner inte product_rate
-- eller kundens ordrar bara själva kunden.
-- Detta för att vi lagt en ON DELETE SET NULL på våra fk_customer_id nycklar
SELECT * FROM product_review;
SELECT * FROM customer;
SELECT * FROM orders;

DELETE FROM customer
WHERE id IN (1);

