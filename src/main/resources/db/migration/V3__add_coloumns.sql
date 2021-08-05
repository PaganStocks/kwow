ALTER TABLE ONLY artists ADD COLUMN "group" TEXT NOT NULL;
ALTER TABLE ONLY artists ADD COLUMN rarity INT NOT NULL CHECK (rarity >= 1 and rarity <= 5);
ALTER TABLE ONLY artists ADD COLUMN version INT NOT NULL CHECK (version >= 1);
