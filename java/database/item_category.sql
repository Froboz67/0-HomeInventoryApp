CREATE TABLE item_category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    is_default BOOLEAN NOT NULL,
    created_by_user_id INTEGER REFERENCES users(user_id)
);
