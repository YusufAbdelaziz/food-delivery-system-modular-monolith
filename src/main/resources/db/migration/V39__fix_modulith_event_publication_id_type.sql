-- Fix existing databases where Modulith publication tables were created with VARCHAR id.
-- Flyway does not rerun V36 once applied, so we patch schema in a new versioned migration.

ALTER TABLE `event_publication`
  MODIFY COLUMN `id` BINARY(16) NOT NULL;

ALTER TABLE `event_publication_archive`
  MODIFY COLUMN `id` BINARY(16) NOT NULL;
