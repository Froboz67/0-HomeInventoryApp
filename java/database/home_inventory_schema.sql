BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, item_rooms, item_documents, item_photos, items, rooms CASCADE;


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

COMMIT TRANSACTION;
