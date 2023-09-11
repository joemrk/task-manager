CREATE PROCEDURE CreateManyWithProcedure(IN tags VARCHAR(512))
BEGIN
    DECLARE tagName VARCHAR(255);
    DECLARE delimiterPosition INT;

    WHILE LENGTH(tags) > 0 DO
        SET delimiterPosition = LOCATE(',', tags);

        IF delimiterPosition = 0 THEN
            SET tagName = tags;
        ELSE
            SET tagName = SUBSTRING(tags, 1, delimiterPosition - 1);
        END IF;

        IF NOT EXISTS (SELECT id FROM tags WHERE name = tagName) THEN
            INSERT INTO tags (name) VALUES (tagName);
        END IF;
    END WHILE;
END;