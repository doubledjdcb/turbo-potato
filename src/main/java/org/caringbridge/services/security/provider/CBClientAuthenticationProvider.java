package org.caringbridge.services.security.provider;

import org.caringbridge.services.security.services.ClientAuthenticationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class CBClientAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private ClientAuthenticationUserService userDetailsService;

	@Override
	protected void doAfterPropertiesSet() throws Exception {
		super.setUserDetailsService(userDetailsService);
		super.doAfterPropertiesSet();
	}
}
