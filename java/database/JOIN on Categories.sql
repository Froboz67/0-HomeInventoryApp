-- INSERT INTO items (user_id, category, category_id)
-- 	VALUES (3, SELECT category_name
-- 	FROM item_category
-- 	WHERE category_id = 1, 1)) 
-- 	RETURNING item_id;

SELECT i.item_id, i.user_id, i.i_name, c.category_name AS category, i.purchase_date, i.purchase_price, i.i_value, i.is_valuable, i.notes, i.created_at, i.updated_at, i.category_id
	FROM items AS i
	JOIN item_category AS c ON i.category_id = c.category_id
	WHERE item_id = 13;
	