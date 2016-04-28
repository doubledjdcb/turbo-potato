package org.caringbridge.services.security.config;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.caringbridge.services.security.provider.CBAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableAuthorizationServer
public class CBAuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private CBAuthenticationProvider cbAuthenticationProvider;

	@Autowired
	private DaoAuthenticationProvider cbClientAuthenticationProvider;


	@Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create(org.apache.commons.dbcp2.BasicDataSource.class.getClassLoader()).build();
    }

	@Resource(name="authenticationManager")
	private ProviderManager authenticationManager;
	
	@Autowired
	private TokenStore tokenStore;
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.tokenServices(tokenServices)
		tokenStore = tokenStore();
	    authenticationManager.getProviders()
	    		.addAll(Arrays.asList(cbClientAuthenticationProvider, cbAuthenticationProvider));
//	    authenticationManager.setEraseCredentialsAfterAuthentication(false);
	    	endpoints.authenticationManager(authenticationManager)
	    	.tokenStore(tokenStore)
	    	.accessTokenConverter(tokenEnhancer());
	}	


	 @Override
	  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

		 oauthServer.allowFormAuthenticationForClients()
		 .addTokenEndpointAuthenticationFilter(customFilter());;
	     oauthServer
	       .tokenKeyAccess("permitAll()") ///oauth/token_key
	       .checkTokenAccess("permitAll()"); ///oauth/check_token
	  }

		private Filter customFilter() {
			return new OncePerRequestFilter() {
				@Override
				protected void doFilterInternal(HttpServletRequest request,
						HttpServletResponse response, FilterChain filterChain)
								throws ServletException, IOException {
//					CsrfToken csrf = (CsrfToken) request
//							.getAttribute(CsrfToken.class.getName());
//					if (csrf != null) {
//						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//						String token = csrf.getToken();
//						if (cookie == null
//								|| token != null && !token.equals(cookie.getValue())) {
//							cookie = new Cookie("XSRF-TOKEN", token);
//							cookie.setPath("/");
//							response.addCookie(cookie);
//						}
//					}
					filterChain.doFilter(request, response);
				}

				@Override
				protected boolean isAsyncDispatch(HttpServletRequest request) {
					// TODO Auto-generated method stub
					return super.isAsyncDispatch(request);
				}

				@Override
				protected boolean isAsyncStarted(HttpServletRequest request) {
					System.out.println("+++++++++++++isAsyncStarted++++++++++++++++++");
					return super.isAsyncStarted(request);
				}

				@Override
				protected String getAlreadyFilteredAttributeName() {
					System.out.println("+++++++++++++getAlreadyFilteredAttributeName++++++++++++++++++"+super.getAlreadyFilteredAttributeName());
					return super.getAlreadyFilteredAttributeName();
				}

				@Override
				protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
					System.out.println("+++++++++++++shouldNotFilter++++++++++++++++++"+super.getAlreadyFilteredAttributeName());
					return super.shouldNotFilter(request);
				}

				@Override
				protected boolean shouldNotFilterAsyncDispatch() {
					System.out.println("+++++++++++++shouldNotFilterAsyncDispatch++++++++++++++++++"+super.getAlreadyFilteredAttributeName());
					return super.shouldNotFilterAsyncDispatch();
				}

				@Override
				protected boolean shouldNotFilterErrorDispatch() {
					System.out.println("+++++++++++++shouldNotFilterErrorDispatch++++++++++++++++++"+super.getAlreadyFilteredAttributeName());
					return super.shouldNotFilterErrorDispatch();
				}
				
				

			};
		}
		
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource());
//	 	clients.inMemory()
//	        .withClient("trusted-api")
//	        	.secret("IWillTrustYou")
//	            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//	            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//	            .scopes("read", "write", "trust")
//	            .resourceIds("cboauth2/trusted"))
//	            .accessTokenValiditySeconds(60)
//		    .and()
//	        .withClient("hallmark-redirect")
//	            .authorizedGrantTypes("authorization_code", "implicit")
//	            .authorities("ROLE_CLIENT")
//	            .autoApprove(true) //or can specify scopes to autoapprove
//	            .scopes("read", "trust")
//	            .secret("redir")
//	            .resourceIds("cboauth2/redirect")
//	            .redirectUris("http://www.kong.dev:8001") //Use any url. 
//		    .and()
//	        .withClient("hallmark")
//	            .authorizedGrantTypes("client_credentials", "password")
//	            .authorities("ROLE_CLIENT")
//	            .scopes("read", "write", "trust")
//	            .resourceIds("cboauth2/secret"))
//	            .secret("secret_password");
	}

	@Bean
	public ApprovalStore approvalStore() throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	
    @Bean
    public JwtAccessTokenConverter tokenEnhancer(){
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("ThisIsAComplexKey");
        return jwtAccessTokenConverter;
    }
	
	@Bean
	public TokenStore tokenStore() {
	    return new JwtTokenStore(tokenEnhancer());
	}
	
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }

}
