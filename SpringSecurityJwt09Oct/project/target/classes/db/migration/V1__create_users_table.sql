/*
  # Create users table for JWT authentication

  1. New Tables
    - `users`
      - `id` (uuid, primary key) - Unique identifier for each user
      - `username` (text, unique, not null) - User's unique username
      - `email` (text, unique, not null) - User's email address
      - `password` (text, not null) - Encrypted password
      - `role` (text, not null, default 'USER') - User role (USER or ADMIN)
      - `created_at` (timestamptz) - Account creation timestamp
      - `updated_at` (timestamptz) - Last update timestamp

  2. Important Notes
    - Passwords should be encrypted before storing (using BCrypt in the application)
    - Default role is set to 'USER' for new registrations
    - Admin role must be assigned manually or through specific registration endpoint
*/

CREATE TABLE IF NOT EXISTS users (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  username text UNIQUE NOT NULL,
  email text UNIQUE NOT NULL,
  password text NOT NULL,
  role text NOT NULL DEFAULT 'USER',
  created_at timestamptz DEFAULT now(),
  updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
