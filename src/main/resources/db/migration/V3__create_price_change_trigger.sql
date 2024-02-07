CREATE OR REPLACE FUNCTION save_price_change()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.price <> OLD.price THEN
        INSERT INTO price_change_history (price_before, price_after, change_data, product_id)
        VALUES (OLD.price, NEW.price, now(), NEW.id);
    END IF;

    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER price_change_trigger
    AFTER UPDATE
    ON products
    FOR EACH ROW
EXECUTE FUNCTION save_price_change();