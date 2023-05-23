-- PLAYER DATA
CREATE TABLE players (
  uuid UUID PRIMARY KEY NOT NULL,
  username VARCHAR(255) NOT NULL,
  first_join TIMESTAMP NOT NULL,
  admin BOOLEAN DEFAULT false,
  discord_user_id BIGINT,
  UNIQUE (uuid, username),
  FOREIGN KEY (discord_user_id) REFERENCES discord_users(id)
);

CREATE TABLE totp_info (
  player_id UUID REFERENCES players(uuid),
  secret VARCHAR(255) NOT NULL UNIQUE,
  locked BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (player_id)
);

CREATE TABLE name_history (
  id SERIAL PRIMARY KEY,
  player_id INTEGER REFERENCES players(id) NOT NULL,
  username VARCHAR(255) NOT NULL,
  changed_at TIMESTAMP NOT NULL
);

CREATE TABLE ip_addresses (
  id SERIAL PRIMARY KEY,
  ip_address bytea UNIQUE NOT NULL
);

CREATE TABLE player_ip_addresses (
  id SERIAL PRIMARY KEY,
  player_id INTEGER REFERENCES players(id) NOT NULL,
  ip_address_id INTEGER REFERENCES ip_addresses(id) NOT NULL,
  accessed_at TIMESTAMP NOT NULL,
  UNIQUE (player_id, ip_address_id)
);

CREATE TABLE session_history (
  id SERIAL PRIMARY KEY,
  player_id INTEGER REFERENCES players(id) NOT NULL,
  session_start TIMESTAMP NOT NULL,
  session_end TIMESTAMP,
  duration INTERVAL
);

-- GAMEMODE DATA
CREATE TABLE gamemodes (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  description TEXT,
  max_players INTEGER DEFAULT NULL,
  whitelist BOOLEAN DEFAULT false
);

CREATE TABLE game_data (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  gamemode_id INTEGER NOT NULL REFERENCES gamemodes(id) ON DELETE CASCADE,
  data JSONB,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE whitelist (
  id SERIAL PRIMARY KEY,
  player_uuid UUID NOT NULL,
  gamemode_id INTEGER NOT NULL,
  FOREIGN KEY (player_uuid) REFERENCES players (uuid),
  FOREIGN KEY (gamemode_id) REFERENCES gamemodes (id)
);

-- GENERALIZE STUFF
CREATE TYPE log_level AS ENUM ('INFO', 'WARN', 'ERROR');

CREATE TABLE logs (
  id SERIAL PRIMARY KEY,
  level log_level NOT NULL,
  message TEXT NOT NULL,
  hash CHAR(64) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);


-- CURRENCIES
CREATE TABLE currency_types (
  id SERIAL PRIMARY KEY,
  currency_type VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE wallet (
  player_uuid UUID NOT NULL REFERENCES players(uuid),
  gamemode_id INTEGER NOT NULL REFERENCES gamemodes(id),
  currency_id INTEGER NOT NULL REFERENCES currency_types(id),
  balance INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (player_uuid, gamemode_id, currency_id),
  CHECK (balance >= 0)
);

CREATE TABLE transactions (
  id SERIAL PRIMARY KEY,
  player_uuid UUID NOT NULL REFERENCES players(uuid),
  gamemode_id INTEGER NOT NULL REFERENCES gamemodes(id),
  currency_id INTEGER NOT NULL REFERENCES currency_types(id),
  amount INTEGER NOT NULL,
  transaction_type VARCHAR(10) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- FRIENDZ
CREATE TABLE friends (
  player1_uuid UUID NOT NULL,
  player2_uuid UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  PRIMARY KEY (player1_uuid, player2_uuid),
  FOREIGN KEY (player1_uuid) REFERENCES players (uuid),
  FOREIGN KEY (player2_uuid) REFERENCES players (uuid),
  CHECK (player1_uuid < player2_uuid)
);

CREATE TABLE pending_friend_requests (
  sender_uuid UUID NOT NULL,
  receiver_uuid UUID NOT NULL,
  created_at TIMESTAMP NOT NULL,
  PRIMARY KEY (sender_uuid, receiver_uuid),
  FOREIGN KEY (sender_uuid) REFERENCES players (uuid),
  FOREIGN KEY (receiver_uuid) REFERENCES players (uuid)
);

CREATE INDEX idx_friends_player1 ON friends (player1_uuid);
CREATE INDEX idx_friends_player2 ON friends (player2_uuid);
CREATE INDEX idx_pending_friend_requests_sender ON pending_friend_requests (sender_uuid);
CREATE INDEX idx_pending_friend_requests_receiver ON pending_friend_requests (receiver_uuid);

-- DISCORD
CREATE TABLE discord_users (
  id BIGINT PRIMARY KEY,
  username TEXT NOT NULL,
  discriminator SMALLINT NOT NULL
);

CREATE TABLE server_boosts (
  id SERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP,
  amount INTEGER NOT NULL,
  FOREIGN KEY (user_id) REFERENCES discord_users(id)
);

CREATE TABLE permissions (
  id SERIAL PRIMARY KEY,
  name TEXT UNIQUE NOT NULL, -- format: "command.example.foo", "command.example.bar"
  -- add any additional permission data fields here
);

CREATE TABLE groups (
  id SERIAL PRIMARY KEY,
  name TEXT UNIQUE NOT NULL, -- unique group name
  parent_id INTEGER REFERENCES groups (id),
  -- add any additional group data fields here
);

CREATE TABLE player_permissions (
  player_id UUID NOT NULL REFERENCES players (id),
  permission_id INTEGER NOT NULL REFERENCES permissions (id),
  is_negated BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (player_id, permission_id)
);

CREATE TABLE group_permissions (
  group_id INTEGER NOT NULL REFERENCES groups (id),
  permission_id INTEGER NOT NULL REFERENCES permissions (id),
  is_negated BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (group_id, permission_id)
);

CREATE TABLE temporary_permissions (
  id SERIAL PRIMARY KEY,
  player_id UUID REFERENCES players (id),
  group_id INTEGER REFERENCES groups (id),
  permission_id INTEGER NOT NULL REFERENCES permissions (id),
  timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  duration INTERVAL,
  is_negated BOOLEAN NOT NULL DEFAULT FALSE,
);

CREATE TABLE player_groups (
  player_id UUID NOT NULL REFERENCES players (id),
  group_id INTEGER NOT NULL REFERENCES groups (id),
  timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  duration INTERVAL,
  PRIMARY KEY (player_id, group_id),
);
