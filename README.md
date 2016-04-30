# cb-authserver
An experiment project to test out Oauth2 using spring. # turbo-potato

`.withClient("hallmark")`
    `.authorizedGrantTypes("client_credentials", "password")`
    `.authorities("ROLE_CLIENT")`
    `.scopes("read", "write", "trust")`
    `.resourceIds("cboauth2/secret")`
    `.accessTokenValiditySeconds(120)`
    `.secret("secret_password");`
