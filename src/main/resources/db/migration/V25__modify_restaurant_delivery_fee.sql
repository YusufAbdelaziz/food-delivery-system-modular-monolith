-- Restaurant delivery fee should reference order_restaurant, not order.
-- Add order_restaurant_id only if it does not exist.
SET
  @column_exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_restaurant_id'
  );

SET
  @sqlstmt := IF(
    @column_exists = 0,
    'ALTER TABLE `restaurant_delivery_fee` ADD COLUMN `order_restaurant_id` bigint unsigned NULL',
    'SELECT "Column order_restaurant_id already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

-- Add FK only if it does not already exist.
SET
  @fk_exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.KEY_COLUMN_USAGE
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_restaurant_id'
      AND REFERENCED_TABLE_NAME = 'order_restaurant'
      AND REFERENCED_COLUMN_NAME = 'order_restaurant_id'
  );

SET
  @sqlstmt := IF(
    @fk_exists = 0,
    'ALTER TABLE `restaurant_delivery_fee`
     ADD CONSTRAINT `fk_restaurant_delivery_fee_order_restaurant`
     FOREIGN KEY (`order_restaurant_id`) REFERENCES `order_restaurant`(`order_restaurant_id`)',
    'SELECT "FK to order_restaurant already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

-- Add index only if it does not already exist.
SET
  @index_exists := (
    SELECT
      COUNT(DISTINCT INDEX_NAME)
    FROM
      INFORMATION_SCHEMA.STATISTICS
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'restaurant_delivery_fee'
      AND COLUMN_NAME = 'order_restaurant_id'
  );

SET
  @sqlstmt := IF(
    @index_exists = 0,
    'ALTER TABLE `restaurant_delivery_fee` ADD INDEX `idx_order_restaurant_id` (`order_restaurant_id`)',
    'SELECT "Index idx_order_restaurant_id already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;
