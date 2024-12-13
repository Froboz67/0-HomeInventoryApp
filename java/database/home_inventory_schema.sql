BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, item_rooms, item_documents, item_photos, items, rooms, item_category CASCADE;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(255) NOT NULL UNIQUE,
	first_name varchar(50),
	last_name varchar(50),
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);
CREATE TABLE items (
    item_id SERIAL, -- primary key constraint
    user_id INT, -- foreign key constraint
    i_name varchar(100) NOT NULL,
    category varchar(50),
    purchase_date DATE,
    purchase_price DECIMAL(10, 2),
    i_value DECIMAL(10, 2),
    is_valuable BOOL, -- boolean
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT PK_item_id PRIMARY KEY (item_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE item_photos (
    photo_id SERIAL, -- primary key
    item_id INT, -- foreign key
    photo_name TEXT,
    photo_url TEXT NOT NULL, -- url path for photo image
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_photo_id PRIMARY KEY (photo_id),
    CONSTRAINT FK_item_id FOREIGN KEY (item_id) REFERENCES items(item_id)
);
CREATE TABLE item_documents (
    document_id SERIAL, -- primary key
    item_id INT,
    room_name varchar(50),
    CONSTRAINT PK_document_id PRIMARY KEY (document_id),
    CONSTRAINT FK_item_id FOREIGN KEY (item_id) REFERENCES items(item_id)
);
CREATE TABLE rooms (
    room_id SERIAL, -- primary key
    user_id INT,
    room_name varchar(50),
    CONSTRAINT PK_room_id PRIMARY KEY (room_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE item_rooms (
    item_room_id SERIAL, -- primary key
    item_id INT, -- associative foreign key
    room_id INT, -- associative foreign key
    CONSTRAINT item_room_id PRIMARY KEY (item_room_id),
    CONSTRAINT FK_item_id FOREIGN KEY (item_id) REFERENCES items(item_id),
    CONSTRAINT FK_room_id FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
CREATE TABLE item_category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    is_default BOOLEAN NOT NULL,
    created_by_user_id INTEGER REFERENCES users(user_id),
	
);


COMMIT TRANSACTION;


-- things that were added after original schema was written
--
--ALTER TABLE items
--ADD COLUMN category_id INTEGER;




-- keeps the names in the two tables constant
--UPDATE items
--SET category_id = ic.category_id
--FROM item_category ic
--WHERE items.category = ic.category_name;

--set foreign key constraint for items table
-- ALTER TABLE items
-- ADD CONSTRAINT fk_item_category
-- FOREIGN KEY (category_id) REFERENCES item_category(category_id);
