BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin@admin.com','$2a$10$jXcwqJHqh5C8gQ/K9945yO9YZ5CA.Wd0ZX/zLNlLHVF7kHTAZIjEa','ROLE_ADMIN');

COMMIT TRANSACTION;
