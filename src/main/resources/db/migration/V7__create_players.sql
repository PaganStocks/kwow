CREATE TABLE players (
    id UUID PRIMARY KEY ,
    discord_id NUMERIC NOT NULL CHECK ( discord_id >= 0 ) UNIQUE,
    balance NUMERIC NOT NULL DEFAULT 0 CHECK ( balance >= 0 ),
    description TEXT DEFAULT ''
);
CREATE UNIQUE INDEX IF NOT EXISTS users_discord_id_in ON players (discord_id);
