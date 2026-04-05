-- Spring Modulith Event Publication Registry tables (MySQL).
-- event_publication is required for durable module event delivery.
-- event_publication_archive is used only if completion-mode=archive.
CREATE TABLE IF NOT EXISTS `event_publication` (
  `id` BINARY(16) NOT NULL,
  `listener_id` VARCHAR(512) NOT NULL,
  `event_type` VARCHAR(512) NOT NULL,
  `serialized_event` VARCHAR(4000) NOT NULL,
  `publication_date` TIMESTAMP(6) NOT NULL,
  `completion_date` TIMESTAMP(6) DEFAULT NULL,
  `status` VARCHAR(20),
  `completion_attempts` INT,
  `last_resubmission_date` TIMESTAMP(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `event_publication_by_completion_date_idx` (`completion_date`)
);

CREATE TABLE IF NOT EXISTS `event_publication_archive` (
  `id` BINARY(16) NOT NULL,
  `listener_id` VARCHAR(512) NOT NULL,
  `event_type` VARCHAR(512) NOT NULL,
  `serialized_event` VARCHAR(4000) NOT NULL,
  `publication_date` TIMESTAMP(6) NOT NULL,
  `completion_date` TIMESTAMP(6) DEFAULT NULL,
  `status` VARCHAR(20),
  `completion_attempts` INT,
  `last_resubmission_date` TIMESTAMP(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `event_publication_archive_by_completion_date_idx` (`completion_date`)
);