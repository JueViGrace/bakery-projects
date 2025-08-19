-- +goose Up
CREATE TABLE IF NOT EXISTS users(
    id TEXT NOT NULL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    alias TEXT NOT NULL DEFAULT '',
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    birth_date TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role TEXT NOT NULL DEFAULT 'user',
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TEXT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS sessions(
    id TEXT NOT NULL PRIMARY KEY,
    refresh_token TEXT NOT NULL,
    access_token TEXT NOT NULL,
    username TEXT NOT NULL,
    user_id TEXT NOT NULL,
    device TEXT NOT NULL DEFAULT '',
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES bakery_user(id)
);

CREATE TABLE IF NOT EXISTS categories(
    id TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS products(
    id TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL DEFAULT '',
    brand TEXT NOT NULL DEFAULT '',
    by_request INTEGER NOT NULL DEFAULT 0,
    discount REAL NOT NULL DEFAULT 0,
    price REAL NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    issued INTEGER NOT NULL,
    images TEXT NOT NULL DEFAULT '',
    category_id TEXT NOT NULL DEFAULT '',
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TEXT DEFAULT NULL,
    FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS product_rating(
    rating INTEGER NOT NULL DEFAULT 0,
    product_id TEXT NOT NULL,
    user_id TEXT NOT NULL,
    PRIMARY KEY(product_id, user_id),
    FOREIGN KEY(product_id) REFERENCES products(id),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS orders(
    id TEXT NOT NULL PRIMARY KEY,
    net_price REAL NOT NULL,
    total_price REAL NOT NULL,
    payment_method TEXT NOT NULL DEFAULT 'cash',
    status TEXT NOT NULL DEFAULT 'placed',
    user_id TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS order_products(
    order_id TEXT NOT NULL,
    product_id TEXT NOT NULL,
    product_name TEXT NOT NULL,
    product_price REAL NOT NULL,
    product_discount REAL NOT NULL,
    quantity INTEGER NOT NULL,
    final_price REAL NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS carts(
    id TEXT NOT NULL PRIMARY KEY,
    total_price REAL NOT NULL,
    user_id TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS cart_products(
    cart_id TEXT NOT NULL,
    product_id TEXT NOT NULL,
    product_name TEXT NOT NULL,
    product_price REAL NOT NULL,
    product_discount REAL NOT NULL,
    quantity INTEGER NOT NULL,
    final_price REAL NOT NULL,
    PRIMARY KEY (cart_id, product_id),
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_orders_user ON orders(user_id);

-- +goose Down
DROP TABLE users;
DROP TABLE categories;
DROP TABLE products;
DROP TABLE product_rating;
DROP TABLE orders;
DROP TABLE order_products;
DROP TABLE sessions;
DROP INDEX IF EXISTS idx_products_category;
DROP INDEX IF EXISTS idx_orders_user;

