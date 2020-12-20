package com.my.application.views.security;


import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

/**
 * Security utils takes care of all such static operation that have to do with
 * security and querying rights from different beans of the UI
 */
public class SecurityUtils {
    private  SecurityUtils() {
        // Util methods only
    }


    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know
     *
     * @param request {@link HttpServletRequest}
     * @return true if it is an internalFramework request . False otherwise.....!
     */
    static  boolean isFrameworkInternalRequest(HttpServletRequest request){
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return  parameterValue !=null && Stream.of(HandlerHelper.RequestType.values())
                .anyMatch(r ->r.getIdentifier().equals(parameterValue));
    }

    /**
     * Tests if some user is authenticated. As Spring Security always will create an
     * {@link AnonymousAuthenticationToken} we have to ignore those tokens explicitly...!
     */

    static boolean isUserLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated();
    }
}
