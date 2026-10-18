-- ==============================================================================
-- LifeSamadhan (.NET Backend) Admin User Seeder Script
-- Email:    admin@mail.com
-- Password: Admin@LifeSamadan123
-- Role:     ADMIN
-- Status:   ACTIVE
-- ==============================================================================

USE `lifesamadhan_db_v2`;

-- Delete existing user with this email or mobile if present
DELETE FROM `users` WHERE `Email` = 'admin@mail.com' OR `Mobile` = '9999999999';

-- Insert Admin with SHA-256 base64 hash used by .NET PasswordService
INSERT INTO `users` (
    `Name`, 
    `Email`, 
    `Mobile`, 
    `Password`, 
    `Role`, 
    `Status`, 
    `CreatedAt`
) VALUES (
    'Admin',
    'admin@mail.com',
    '9999999999',
    '4+h7/uvNKc5PKWEzH03rjHBxZcASltH5QPHPNmkbjDU=',
    'ADMIN',
    'ACTIVE',
    NOW()
);

-- Verification:
SELECT `Id`, `Name`, `Email`, `Mobile`, `Role`, `Status`, `CreatedAt` 
FROM `users` 
WHERE `Email` = 'admin@mail.com';
