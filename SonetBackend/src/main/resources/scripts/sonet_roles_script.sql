use spring_init;

INSERT INTO authority(name) VALUES("canListenSongs"), ("canUploadSongs");

INSERT INTO role(name) VALUES("ROLE_LISTENER"), ("ROLE_ARTIST");

INSERT INTO role_authority (role_id, authority_id)
VALUES
        ((SELECT id FROM role WHERE name = 'ROLE_LISTENER'), (SELECT id FROM authority WHERE name = 'canListenSongs')),
        ((SELECT id FROM role WHERE name = 'ROLE_ARTIST'), (SELECT id FROM authority WHERE name = 'canListenSongs')),
        ((SELECT id FROM role WHERE name = 'ROLE_ARTIST'), (SELECT id FROM authority WHERE name = 'canUploadSongs'));
