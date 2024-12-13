UPDATE items
SET category_id = (
	SELECT category_id 
		FROM item_category
		WHERE category_name = 'Musical Instrument'
) WHERE item_id = 1;


-- adding name to category field

UPDATE items
SET category = (
	SELECT category_name 
		FROM item_category
		WHERE category_name = 'Furniture'
) WHERE item_id = 3;