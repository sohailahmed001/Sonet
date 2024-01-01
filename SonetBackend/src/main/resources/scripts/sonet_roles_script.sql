use spring_init;

-- default password: admin12345
INSERT INTO user(username, password, created_date)
VALUES('admin', '$2a$12$tnFMsdV1WxiRTPbY.bzvoukp8WeTsoCd.WcIIYJc1zXeBLQRDRZni', now(6))

INSERT INTO authority(name) VALUES("canListenSongs"), ("canUploadSongs");

INSERT INTO role(name) VALUES("ROLE_ADMIN"), ("ROLE_LISTENER"), ("ROLE_ARTIST");

INSERT INTO user_role(user_id, role_id)
VALUES
        ((SELECT id FROM user WHERE username = 'admin'), (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));

INSERT INTO role_authority (role_id, authority_id)
VALUES
        ((SELECT id FROM role WHERE name = 'ROLE_LISTENER'), (SELECT id FROM authority WHERE name = 'canListenSongs')),
        ((SELECT id FROM role WHERE name = 'ROLE_ARTIST'), (SELECT id FROM authority WHERE name = 'canListenSongs')),
        ((SELECT id FROM role WHERE name = 'ROLE_ARTIST'), (SELECT id FROM authority WHERE name = 'canUploadSongs'));
