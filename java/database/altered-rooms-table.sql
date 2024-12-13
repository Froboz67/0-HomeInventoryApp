SELECT room_id, room_name, is_default, created_by_user_id
	FROM rooms;

SELECT room_id, room_name, is_default, created_by_user_id
	FROM rooms
	WHERE is_default = true
		OR created_by_user_id = (SELECT user_id FROM users WHERE user_id = 3);


-- ALTER TABLE rooms
-- 	ADD CONSTRAINT FK_created_by_user_id FOREIGN KEY (created_by_user_id) REFERENCES users(user_id);

-- ALTER TABLE rooms
-- 	ADD created_by_user_id INT; 

-- ALTER TABLE rooms
-- 	ADD user_id INT NOT NULL; 

-- ALTER TABLE rooms
-- 	ADD room_name varchar(50); 

-- ALTER TABLE rooms
-- 	DROP COLUMN created_by_user_id;

-- ALTER TABLE rooms
-- 	DROP COLUMN is_default;

-- ALTER TABLE rooms
-- 	DROP COLUMN user_id;

-- ALTER TABLE rooms
-- 	ADD is_default BOOLEAN DEFAULT TRUE;

INSERT INTO rooms (room_name, is_default, created_by_user_id)
VALUES ('Monkey Room', FALSE, 3);



