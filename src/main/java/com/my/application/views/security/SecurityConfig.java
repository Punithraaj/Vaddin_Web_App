package com.my.application.views.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROC_URL ="/login";
    private static final String LOGIN_FAILURE_URL ="/login?error";
    private static final String LOGIN_URL ="/login";
    private static final String LOGOUT_SUCCESS_URL ="/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Not using Spring CSRF here to be able to use plain HTML for the login page

        http.csrf().disable()
                /**
                 * Register our CustomRequestCache, that saves unauthoried access attempts
                 * so the user is redirected after login.
                 */
                .requestCache().requestCache(new CustomRequestCache())
                //Restrict access to our application
                .and().authorizeRequests()
                //Allow all vaadin internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                //Allow all requests by logged in users
                .anyRequest().authenticated()
                //Configure the login page
                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROC_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                //Configure the logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    /**
     * Allows access to static resources, bypassing Spring security..
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                //Client-side js
                "/VAADIN/**",
                //the standared favicon URI
                "/favicon.ico",
                //the robts exclusion standard
                "/robots.txt",

                //web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",

                //icons and images
                "/icons/**",
                "/images/**",
                "/styles/**",
                //(development mode)H2 debugging console
                "/h2-console/**"
        );
    }
}
