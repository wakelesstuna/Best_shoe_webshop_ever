START TRANSACTION;

UPDATE product 
SET
    stock = stock - 2
WHERE
    id = "1";

UPDATE orders_product
SET
    quantity = quantity + 2
WHERE
    id = "10";

COMMIT;