CREATE TABLE IF NOT EXISTS "public"."bakery_user"(
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    birth_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    phone VARCHAR(25) NOT NULL,
    role varchar(10) NOT NULL DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS "public"."bakery_product"(
    id SERIAL NOT NULL PRIMARY KEY,
    price NUMERIC NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT DEFAULT '',
    category VARCHAR(255) NOT NULL DEFAULT '',
    stock INT NOT NULL DEFAULT 0,
    image TEXT DEFAULT '',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS "public"."bakery_token"(
    user_email TEXT NOT NULL PRIMARY KEY,
    TOKEN TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "public"."bakery_order"(
    id SERIAL NOT NULL PRIMARY KEY,
    total_amount REAL NOT NULL,
    payment_method VARCHAR(20) NOT NULL DEFAULT 'cash',
    status VARCHAR(30) NOT NULL DEFAULT 'placed',
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY(user_id) REFERENCES bakery_user(id)
);

CREATE TABLE IF NOT EXISTS "public"."bakery_order_products"(
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    price REAL NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY(order_id) REFERENCES bakery_order(id),
    FOREIGN KEY(product_id) REFERENCES bakery_product(id)
);

CREATE TABLE IF NOT EXISTS "public"."bakery_cart" (
    id INT NOT NULL PRIMARY KEY,
    total_amount REAL NOT NULL DEFAULT 0.0,
    FOREIGN KEY(id) REFERENCES bakery_user(id)
);

CREATE TABLE IF NOT EXISTS "public"."bakery_cart_products" (
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(cart_id, product_id),
    FOREIGN KEY(cart_id) REFERENCES bakery_cart(id),
    FOREIGN KEY(product_id) REFERENCES bakery_product(id)
);
