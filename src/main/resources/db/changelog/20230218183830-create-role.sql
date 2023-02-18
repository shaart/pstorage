--liquibase formatted sql

--changeset imshaart@gmail.com:20230218183830-create-role-create-table-role
CREATE TABLE dct_roles (
  id UUID PRIMARY KEY,
  name VARCHAR(255) UNIQUE NOT NULL,
  created_at TIMESTAMP DEFAULT now()
);

--changeset imshaart@gmail.com:20230218183830-create-role-create-admin-role
INSERT INTO dct_roles (id, name, created_at) VALUES (random_uuid(), 'ADMIN', now());

--changeset imshaart@gmail.com:20230218183830-create-role-create-user-role
INSERT INTO dct_roles (id, name, created_at) VALUES (random_uuid(), 'USER', now());

--changeset imshaart@gmail.com:20230218183830-create-role-add-column-role_id-to-user
ALTER TABLE usr_users
  ADD COLUMN role_id UUID NOT NULL DEFAULT (SELECT id FROM dct_roles WHERE name = 'USER');
ALTER TABLE usr_users
  ADD FOREIGN KEY (role_id) REFERENCES dct_roles(id);
