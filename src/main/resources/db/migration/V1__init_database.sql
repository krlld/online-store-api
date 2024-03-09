CREATE TABLE categories
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT    NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE products
(
    id             BIGSERIAL PRIMARY KEY,
    name           TEXT        NOT NULL,
    price          DECIMAL     NOT NULL,
    description    TEXT        NOT NULL,
    average_rating DECIMAL     NOT NULL DEFAULT 0,
    total_reviews  INT         NOT NULL DEFAULT 0,
    category_id    BIGINT      NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_deleted     BOOLEAN     NOT NULL DEFAULT FALSE,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE price_change_history
(
    id           BIGSERIAL PRIMARY KEY,
    price_before DECIMAL     NOT NULL,
    price_after  DECIMAL     NOT NULL,
    change_data  TIMESTAMPTZ NOT NULL,
    product_id   BIGINT      NOT NULL,
    is_deleted   BOOLEAN     NOT NULL DEFAULT FALSE,
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE images
(
    id         BIGSERIAL PRIMARY KEY,
    url        TEXT    NOT NULL,
    product_id BIGINT  NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      TEXT    NOT NULL,
    password   TEXT    NOT NULL,
    name       TEXT    NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE roles
(
    name       TEXT PRIMARY KEY,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_roles
(
    user_id   BIGINT,
    role_name TEXT,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_name) REFERENCES roles (name)
);

CREATE TABLE favorite_items
(
    user_id    BIGINT,
    product_id BIGINT,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE cart_items
(
    user_id    BIGINT,
    product_id BIGINT,
    quantity   INT NOT NULL,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE order_statuses
(
    name       TEXT PRIMARY KEY,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE orders
(
    id                BIGSERIAL PRIMARY KEY,
    date              DATE    NOT NULL,
    delivery_address  TEXT    NOT NULL,
    user_id           BIGINT  NOT NULL,
    order_status_name TEXT    NOT NULL,
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (order_status_name) REFERENCES order_statuses (name)
);

CREATE TABLE order_items
(
    id         BIGSERIAL PRIMARY KEY,
    price      DECIMAL NOT NULL,
    quantity   INT     NOT NULL,
    order_id   BIGINT  NOT NULL,
    product_id BIGINT  NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE reviews
(
    id         BIGSERIAL PRIMARY KEY,
    content    TEXT        NOT NULL,
    rating     INT         NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    user_id    BIGINT      NOT NULL,
    product_id BIGINT      NOT NULL,
    is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);