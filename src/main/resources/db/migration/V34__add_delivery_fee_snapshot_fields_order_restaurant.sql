-- OrderRestaurant stores a snapshot of delivery fee values at order time.
-- Add missing columns in an idempotent way.

SET
  @exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'order_restaurant'
      AND COLUMN_NAME = 'delivery_fee_current_price'
  );

SET
  @sqlstmt := IF(
    @exists = 0,
    'ALTER TABLE `order_restaurant` ADD COLUMN `delivery_fee_current_price` decimal(10, 2) NULL',
    'SELECT "Column delivery_fee_current_price already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

SET
  @exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'order_restaurant'
      AND COLUMN_NAME = 'delivery_fee_discounted_price'
  );

SET
  @sqlstmt := IF(
    @exists = 0,
    'ALTER TABLE `order_restaurant` ADD COLUMN `delivery_fee_discounted_price` decimal(10, 2) NULL',
    'SELECT "Column delivery_fee_discounted_price already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

SET
  @exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'order_restaurant'
      AND COLUMN_NAME = 'estimated_delivery_duration_seconds'
  );

SET
  @sqlstmt := IF(
    @exists = 0,
    'ALTER TABLE `order_restaurant` ADD COLUMN `estimated_delivery_duration_seconds` INT UNSIGNED NULL',
    'SELECT "Column estimated_delivery_duration_seconds already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;
