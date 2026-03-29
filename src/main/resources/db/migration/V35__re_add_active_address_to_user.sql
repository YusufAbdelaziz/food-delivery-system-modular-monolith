-- Re-add active_address_id to user to match current entity mapping.

-- Add active_address_id column if it does not exist.
SET
  @column_exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.COLUMNS
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'user'
      AND COLUMN_NAME = 'active_address_id'
  );

SET
  @sqlstmt := IF(
    @column_exists = 0,
    'ALTER TABLE `user` ADD COLUMN `active_address_id` bigint unsigned NULL',
    'SELECT "Column active_address_id already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;

-- Add FK to address(address_id) if it does not already exist.
SET
  @fk_exists := (
    SELECT
      COUNT(*)
    FROM
      INFORMATION_SCHEMA.KEY_COLUMN_USAGE
    WHERE
      TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'user'
      AND COLUMN_NAME = 'active_address_id'
      AND REFERENCED_TABLE_NAME = 'address'
      AND REFERENCED_COLUMN_NAME = 'address_id'
  );

SET
  @sqlstmt := IF(
    @fk_exists = 0,
    'ALTER TABLE `user`
     ADD CONSTRAINT `fk_user_active_address`
     FOREIGN KEY (`active_address_id`) REFERENCES `address`(`address_id`)',
    'SELECT "FK for active_address_id already exists"'
  );

PREPARE stmt
FROM
  @sqlstmt;

EXECUTE stmt;

DEALLOCATE PREPARE stmt;
