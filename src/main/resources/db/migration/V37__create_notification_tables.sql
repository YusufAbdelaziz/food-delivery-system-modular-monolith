CREATE TABLE IF NOT EXISTS `notification_channels` (
  `id` bigint unsigned AUTO_INCREMENT,
  `channel_type` ENUM('EMAIL', 'SMS', 'WHATSAPP') NOT NULL,
  `is_active` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_notification_channels_type` (`channel_type`)
);

CREATE TABLE IF NOT EXISTS `notifications` (
  `id` bigint unsigned AUTO_INCREMENT,
  `user_id` bigint unsigned,
  `channel_id` bigint unsigned NOT NULL,
  `message` TEXT,
  `recipient_info` JSON,
  `status` ENUM('PENDING', 'SENT', 'FAILED') NOT NULL DEFAULT 'PENDING',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_notifications_user_id` (`user_id`),
  KEY `idx_notifications_channel_id` (`channel_id`),
  CONSTRAINT `fk_notifications_channel`
    FOREIGN KEY (`channel_id`) REFERENCES `notification_channels` (`id`)
);

INSERT INTO `notification_channels` (`channel_type`, `is_active`)
VALUES
  ('EMAIL', TRUE),
  ('SMS', TRUE),
  ('WHATSAPP', TRUE)
ON DUPLICATE KEY UPDATE `is_active` = VALUES(`is_active`);
