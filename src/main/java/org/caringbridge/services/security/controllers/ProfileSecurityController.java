package org.caringbridge.services.security.controllers;

import org.caringbridge.services.security.model.Profile;
import org.caringbridge.services.security.services.ProfileUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * Controller for handling the request by mapping url.
 * 
 * @author Simi George
 *
 */
@RestController
@Api(basePath = "/oauth", description = "The security api for demonstration purposes.", value = "security")
@RequestMapping(path = "/oauth")
public class ProfileSecurityController {

    @Autowired
    ProfileUserDetailsService profileLoginService;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "authenticate", httpMethod = "POST", notes = "Responds with a success result if the service is healthy.", produces = "application/json")
    @ApiResponse(code = 404, message = "No Information Found for this Provider")
    public @ResponseBody ResponseEntity<Profile> authenticate(
	    @RequestParam(name = "userid", required = true) String userId,
	    @RequestParam(name = "password", required = true) String password) {

	return ResponseEntity.ok(profileLoginService.authenticate(userId, password));
    }

}
