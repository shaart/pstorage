--liquibase formatted sql

--changeset imshaart@gmail.com:20230218183605-encrypt-type-add-column-encrypt-type-to-password
ALTER TABLE usr_passwords
  ADD COLUMN encrypt_type VARCHAR(20) NOT NULL DEFAULT 'AES_CODER';

--changeset imshaart@gmail.com:20230218183605-encrypt-type-add-column-encrypt-type-to-user
ALTER TABLE usr_users
  ADD COLUMN encrypt_type VARCHAR(20) NOT NULL DEFAULT 'AES_CODER';
