INSERT INTO oauth_client_details(client_id,
                                 resource_ids,
                                 client_secret,
                                 scope,
                                 authorized_grant_types,
                                 web_server_redirect_uri,
                                 authorities,
                                 access_token_validity,
                                 refresh_token_validity,
                                 additional_information,
                                 autoapprove)
     VALUES ('hallmark',
             'cboauth2/secret',
             'secret_password',
             'read,write,trust',
             'client_credentials,password',
             NULL,
             'ROLE_CLIENT',
             120,
             NULL,
             NULL,
             NULL);

INSERT INTO oauth_client_details(client_id,
                                 resource_ids,
                                 client_secret,
                                 scope,
                                 authorized_grant_types,
                                 web_server_redirect_uri,
                                 authorities,
                                 access_token_validity,
                                 refresh_token_validity,
                                 additional_information,
                                 autoapprove)
     VALUES ('hallmark-redirect',
             'cboauth2/trusted',
             'redir',
             'read',
             'authorization_code,implicit',
             'http://www.kong.dev:8001',
             'ROLE_USER',
             NULL,
             NULL,
             NULL,
             'true');

INSERT INTO oauth_client_details(client_id,
                                 resource_ids,
                                 client_secret,
                                 scope,
                                 authorized_grant_types,
                                 web_server_redirect_uri,
                                 authorities,
                                 access_token_validity,
                                 refresh_token_validity,
                                 additional_information,
                                 autoapprove)
     VALUES ('trusted-api',
             'cboauth2/trusted',
             'IWillTrustYou',
             'read,write,trust',
             'password,authorization_code,refresh_token',
             NULL,
             'ROLE_CLIENT,ROLE_TRUSTED_CLIENT',
             60,
             NULL,
             NULL,
             NULL);

INSERT INTO authorities(username, authority)
     VALUES ('hallmark', 'ROLE_CLIENT');

INSERT INTO authorities(username, authority)
     VALUES ('hallmark-redir', 'ROLE_USER');

INSERT INTO authorities(username, authority)
     VALUES ('trusted-api', 'ROLE_TRUSTED_CLIENT');

INSERT INTO authorities(username, authority)
     VALUES ('hallmark', 'ROLE_TRUSTED_CLIENT');

INSERT INTO users(username, password, enabled)
     VALUES ('hallmark', 'secret_password', TRUE);

INSERT INTO users(username, password, enabled)
     VALUES ('trusted-api', 'IWillTrustYou', TRUE);

INSERT INTO users(username, password, enabled)
     VALUES ('hallmark-redir', 'redir', TRUE);

COMMIT;