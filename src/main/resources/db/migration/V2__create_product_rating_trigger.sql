CREATE OR REPLACE FUNCTION update_product_rating()
    RETURNS TRIGGER AS
$$
DECLARE
    new_total_reviews  INT;
    new_average_rating DECIMAL;
BEGIN
    SELECT COUNT(*)
    INTO new_total_reviews
    FROM reviews
    WHERE product_id = NEW.product_id;

    IF new_total_reviews > 0 THEN
        SELECT SUM(rating) / new_total_reviews
        INTO new_average_rating
        FROM reviews
        WHERE product_id = NEW.product_id;
    ELSE
        new_average_rating := 0;
    END IF;

    UPDATE products
    SET average_rating = new_average_rating,
        total_reviews  = new_total_reviews
    WHERE id = NEW.product_id;

    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER update_product_rating_trigger
    AFTER INSERT
    ON reviews
    FOR EACH ROW
EXECUTE FUNCTION update_product_rating();
