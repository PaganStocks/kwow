DO
$do$
    DECLARE
        groupname TEXT;
    BEGIN
        FOR groupname IN (SELECT DISTINCT "group" FROM artists) LOOP
            INSERT INTO groups (name) VALUES (groupname) ON CONFLICT DO NOTHING;
            UPDATE artists SET "group" = (SELECT CAST(id AS text) FROM groups WHERE name = groupname) WHERE "group" = groupname;
        END LOOP;
    END
$do$;

ALTER TABLE artists
    ALTER COLUMN "group" SET DATA TYPE uuid USING "group"::uuid;
ALTER TABLE artists
    ADD CONSTRAINT artist_group_fk FOREIGN KEY ("group") REFERENCES "groups" (id);