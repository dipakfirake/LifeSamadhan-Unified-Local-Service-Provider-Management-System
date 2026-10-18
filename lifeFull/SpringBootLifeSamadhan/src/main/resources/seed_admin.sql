-- ==============================================================================
-- LifeSamadhan Admin User Seeder Script
-- Email:    admin@mail.com
-- Password: Admin@LifeSamadan123
-- Role:     ADMIN
-- Status:   ACTIVE
-- ==============================================================================

USE `lifesamadhan_spring`;

-- Delete existing admin record if present to avoid duplicate email/mobile conflicts
DELETE FROM `users` WHERE `email` = 'admin@mail.com' OR `mobile` = '9999999999';

-- Insert Admin User with verified BCrypt hash
INSERT INTO `users` (
    `name`,
    `email`,
    `mobile`,
    `password`,
    `role`,
    `status`,
    `created_at`
) VALUES (
    'Admin',
    'admin@mail.com',
    '9999999999',
    '$2a$10$eD9GIt0mKzRXbsMEGpwBeOlhtTiUC8J7u4fCXqLK/WyG/ZA1jjajy',
    'ADMIN',
    'ACTIVE',
    NOW()
);

-- Verification Query:
SELECT `id`, `name`, `email`, `mobile`, `role`, `status`, `created_at` 
FROM `users` 
WHERE `email` = 'admin@mail.com';
