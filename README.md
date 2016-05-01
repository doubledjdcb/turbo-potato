# cb-authserver
An experiment project to test out Oauth2 using spring. # turbo-potato

### Client 1 - hallmark

`.withClient("hallmark")` <br /> 
&nbsp;&nbsp;&nbsp;    `.authorizedGrantTypes("client_credentials", "password")` <br /> 
&nbsp;&nbsp;&nbsp;    `.authorities("ROLE_CLIENT")` <br /> 
&nbsp;&nbsp;&nbsp;    `.scopes("read", "write", "trust")` <br /> 
&nbsp;&nbsp;&nbsp;    `.resourceIds("cboauth2/secret")` <br /> 
&nbsp;&nbsp;&nbsp;    `.accessTokenValiditySeconds(120)` <br /> 
&nbsp;&nbsp;&nbsp;    `.secret("secret_password");` <br /> 

##### Get Access token - Client Credentials with hallmark/secret_password

Oauth2 token url - ``http://localhost:11001/oauth/token?grant_type=client_credentials``<br />
Header (Base64 encoded - hallmark:secret_password) - ``Authorization: Basic aGFsbG1hcms6c2VjcmV0X3Bhc3N3b3Jk``

``curl -v -X POST http://localhost:11001/oauth/token -H "Authorization: Basic aGFsbG1hcms6c2VjcmV0X3Bhc3N3b3Jk" -d "grant_type=client_credentials"``

##### Get Access token - Password with hallmark/secret_password

Oauth2 token url - ``http://localhost:11001/oauth/token?grant_type=password&password=secret_password&username=hallmark``<br />
Header (Base64 encoded - hallmark:secret_password) - ``Authorization: Basic aGFsbG1hcms6c2VjcmV0X3Bhc3N3b3Jk``

``curl -X POST http://localhost:11001/oauth/token -H 'Authorization: Basic aGFsbG1hcms6c2VjcmV0X3Bhc3N3b3Jk=' -d 'password=secret_password&username=hallmark&grant_type=password' ``

##### Response with access token

``{``<br /> 
&nbsp;&nbsp;&nbsp;    ``"access_token": "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiY2JvYXV0aDIvc2VjcmV0Il0sInVzZXJfbmFtZSI6ImhhbGxtYXJrIiwic2NvcGUiOlsicmVhZFwiIiwiIFwid3JpdGVcIiIsIiBcInRydXN0Il0sImV4cCI6MTQ2MTU1NTY0OSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9DTElFTlQiXSwianRpIjoiODRjNmI0MWYtNWE0Ny00N2U4LWI5ZmItZGM1MTk3ZTMyNzM1IiwiY2xpZW50X2lkIjoiaGFsbG1hcmsifQ.J_QReA97ZGAx02Nl1VBFYNaLV-lrIOWLnNg9nWvGbYI",``<br /> 
&nbsp;&nbsp;&nbsp;    ``"token_type": "bearer",``<br /> 
&nbsp;&nbsp;&nbsp;    ``"expires_in": 119,``<br /> 
&nbsp;&nbsp;&nbsp;    ``"scope": "read\"  \"write\"  \"trust",``<br /> 
&nbsp;&nbsp;&nbsp;    ``"jti": "84c6b41f-5a47-47e8-b9fb-dc5197e32735"``<br /> 
``}``

-----
### Client 2 - hallmark-redirect

`.withClient("hallmark-redirect")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .authorizedGrantTypes("authorization_code", "implicit")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .authorities("ROLE_CLIENT")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .autoApprove(true) //or can specify scopes to autoapprove` <br /> 
&nbsp;&nbsp;&nbsp;    `    .scopes("read", "trust")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .secret("redir")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .resourceIds("cboauth2/redirect")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .redirectUris("http://www.kong.dev:8001") //Use any url. ` <br /> 

##### Get Authorization Code - Authorization Code grant type. 
+ Has to go through authorization flow 
+ If client is not authenticated, it will popup basic auth login
+ In this example, the client id and the username has to be the same and has to exist in both tables. This can be by-passed with a custom authentication provider
+ The redirect_uri in the authorization call needs to the same as the one registered by the client with the authorization server
+ The authorization code will be added when the client is redirected to the redirect uri

Oauth2 authorization url - ``http://localhost:11001/oauth/authorize?response_type=code&client_id=hallmark-redirect&redirect_uri=http://www.kong.dev:8001&client_secret=redir``<br />

Redirects to - ``http://www.kong.dev:8001/?code=r76YlN``

######Use the code to request access_token######

Oauth2 token url - ``http://localhost:11001/oauth/token?grant_type=authorization_code&code=r76YlN&redirect_uri=http://www.kong.dev:8001``

##### Get Access Token directly - Implicit grant type. 

Oauth2 authorization url - ``http://localhost:11001/oauth/authorize?response_type=token&client_id=hallmark-redirect&redirect_uri=http://www.kong.dev:8001&client_secret=redir``<br />

Redirects to - ``http://www.kong.dev:8001/#access_token=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiY2JvYXV0aDIvdHJ1c3RlZCJdLCJ1c2VyX25hbWUiOiJzZ2VvcmdlQGNhcmluZ2JyaWRnZS5vcmciLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTQ2MTYzOTQ1NCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImExZDZiYjE3LTA5M2ItNDFjZS1hOWQwLWQ4ZTA3NzQ5ZmY3YyIsImNsaWVudF9pZCI6ImhhbGxtYXJrLXJlZGlyZWN0In0.g82VDG6n-HaCu543q982c79rNm_mZpxutbz9aAAWozI&token_type=bearer&expires_in=43199&scope=read&jti=a1d6bb17-093b-41ce-a9d0-d8e07749ff7cN``

-----
### Client 3 - trusted-api

`.withClient("trusted-api")` <br /> 
&nbsp;&nbsp;&nbsp;    `	.secret("IWillTrustYou")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .authorizedGrantTypes("password", "authorization_code", "refresh_token")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .scopes("read", "write", "trust")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .resourceIds("cboauth2/trusted")` <br /> 
&nbsp;&nbsp;&nbsp;    `    .accessTokenValiditySeconds(60)` <br /> 

##### Get Access token -  Client Id = trusted-api | User with hallmark/secret_password

+ We can use any user that has the correct role
+ Instead of passing the client credentials in the header, we can also pass it in the url as `&client_id=trusted-api&client_secret=IWillTrustYou`

Oauth2 token url - ``http://localhost:11001/oauth/token?grant_type=password&username=hallmark&password=secret_password``<br />
Header (Base64 encoded - trusted-api:IWillTrustYou) - ``Authorization: Basic dHJ1c3RlZC1hcGk6SVdpbGxUcnVzdFlvdQ==``

``curl -v -X POST http://localhost:11001/oauth/token -H "Authorization: Basic dHJ1c3RlZC1hcGk6SVdpbGxUcnVzdFlvdQ==" -d "grant_type=password&username=hallmark&password=secret_password"``

##### Response with access token

``{` <br /> 
&nbsp;&nbsp;&nbsp;    `	    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiY2JvYXV0aDIvdHJ1c3RlZCJdLCJ1c2VyX25hbWUiOiJoYWxsbWFyayIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTQ2MTYwMDAzOSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCIsIlJPTEVfQ0xJRU5UIl0sImp0aSI6IjBjZjk2MTNjLWMxMjgtNDY3My1hNmI4LTA1YTMzZTM5NjAwZiIsImNsaWVudF9pZCI6InRydXN0ZWQtYXBpIn0.5km7yRj1GD6ZjD7vC2O3nZkLDMVsW4Araye4aApfzQw",` <br /> 
&nbsp;&nbsp;&nbsp;    `	    "token_type": "bearer",` <br /> 
&nbsp;&nbsp;&nbsp;    `	    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiY2JvYXV0aDIvdHJ1c3RlZCJdLCJ1c2VyX25hbWUiOiJoYWxsbWFyayIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjBjZjk2MTNjLWMxMjgtNDY3My1hNmI4LTA1YTMzZTM5NjAwZiIsImV4cCI6MTQ2NDE5MTk3OSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCIsIlJPTEVfQ0xJRU5UIl0sImp0aSI6ImE1ODU1ZDFkLTg2NmEtNGVlZS1hYWJhLWQ1Zjc5MjI5NDU2ZSIsImNsaWVudF9pZCI6InRydXN0ZWQtYXBpIn0.6pDHzqEusYevWfaxZ6yTU1ZcDlhHqY9wF7b0hAtA-0g",` <br /> 
&nbsp;&nbsp;&nbsp;    `	    "expires_in": 59,` <br /> 
&nbsp;&nbsp;&nbsp;    `	    "scope": "read write trust",` <br /> 
&nbsp;&nbsp;&nbsp;    `	    "jti": "0cf9613c-c128-4673-a6b8-05a33e39600f"` <br /> 
`}`<br /> 

##### Get Access token from refresh_token

Make the call with the parameters `grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiJ9.......`
