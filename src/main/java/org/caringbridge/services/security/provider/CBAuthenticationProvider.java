package org.caringbridge.services.security.provider;

import org.caringbridge.services.security.services.BCryptUtility;
import org.caringbridge.services.security.services.ProfileUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private ProfileUserDetailsService userService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = userService.loadUserByUsername(username);
        
        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }
 
        if (!BCryptUtility.authenticate(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
	    UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	
	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$additionalAuthenticationChecks$$$$$$$$$$$$$$$$$$$$$$" + authentication.getName());
	// TODO Auto-generated method stub
	
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
	    throws AuthenticationException {
	System.out.println("$$$$$$$$$$$$$$$$$$$$$$retrieveUser$$$$$$$$$$$$$$$$$$$$$$$" + authentication.getName());
	return (UserDetails) authentication.getPrincipal();
    }

}
