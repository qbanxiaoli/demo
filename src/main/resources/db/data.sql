-- 初始化数据
INSERT INTO `demo`.`oauth_client_details` (`client_id`, `access_token_validity`, `additional_information`,
                                                   `authorities`, `authorized_grant_types`, `autoapprove`,
                                                   `client_secret`, `refresh_token_validity`, `resource_ids`, `scope`,
                                                   `web_server_redirect_uri`)
VALUES ('quantization', NULL, NULL, NULL, 'password,refresh_token', '',
        '$2a$10$13Hq6ZTX5oREnVTiLvN7HOSTfA9QgTqd.0UbetZsPgiOyoffd/7ae', NULL, NULL, 'read', NULL);

INSERT INTO `demo`.`sys_role`(`id`, `authority`)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_ANONYMOUS');