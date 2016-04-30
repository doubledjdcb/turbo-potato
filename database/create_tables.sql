CREATE SCHEMA oauth;

CREATE TABLE users
(
   username   varchar(256),
   password   varchar(256),
   enabled    tinyint(1)
);

CREATE TABLE authorities
(
   username    varchar(256),
   authority   varchar(256)
);

CREATE TABLE oauth_client_details
(
   client_id                 varchar(256),
   resource_ids              varchar(256),
   client_secret             varchar(256),
   scope                     varchar(256),
   authorized_grant_types    varchar(256),
   web_server_redirect_uri   varchar(256),
   authorities               varchar(256),
   access_token_validity     int(11),
   refresh_token_validity    int(11),
   additional_information    varchar(4096),
   autoapprove               varchar(256)
);

CREATE TABLE oauth_client_token
(
   token_id            varchar(256),
   token               mediumblob,
   authentication_id   varchar(256),
   user_name           varchar(256),
   client_id           varchar(256)
);

CREATE TABLE oauth_code
(
   code             varchar(256),
   authentication   mediumblob
);

CREATE TABLE oauth_access_token
(
   token_id            varchar(256),
   token               mediumblob,
   authentication_id   varchar(256),
   user_name           varchar(256),
   client_id           varchar(256),
   authentication      mediumblob,
   refresh_token       varchar(256)
);

CREATE TABLE oauth_refresh_token
(
   token_id         varchar(256),
   token            mediumblob,
   authentication   mediumblob
);