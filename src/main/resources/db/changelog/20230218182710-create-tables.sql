--liquibase formatted sql

--changeset imshaart@gmail.com:20230218182710-create-tables
CREATE TABLE usr_users (
  id UUID PRIMARY KEY,
  name VARCHAR(255) UNIQUE NOT NULL,
  master_password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE usr_passwords (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  alias VARCHAR(255) NOT NULL UNIQUE,
  encrypted_value VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT now(),
  FOREIGN KEY (user_id) REFERENCES usr_users(id)
);
