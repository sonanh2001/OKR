package org.aibles.okr3.configuration;

import static org.aibles.okr3.constants.ApiConstants.KEY_RESULTS_URI;
import static org.aibles.okr3.constants.ApiConstants.OBJECTIVES_URI;
import static org.aibles.okr3.constants.ApiConstants.REFRESH_TOKEN_URI;
import static org.aibles.okr3.constants.ApiConstants.USERS_API_URI;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import lombok.RequiredArgsConstructor;
import org.aibles.okr3.filter.CustomAuthorizationFilter;
import org.aibles.okr3.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final UserService userService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers(GET, REFRESH_TOKEN_URI + "/**").permitAll();
    http.authorizeRequests().antMatchers(POST, USERS_API_URI + "/**").permitAll();
    http.authorizeRequests().antMatchers(GET, USERS_API_URI + "/**").authenticated();
    http.authorizeRequests().antMatchers(PUT, USERS_API_URI + "/**").authenticated();
    http.authorizeRequests().antMatchers(OBJECTIVES_URI + "/**").authenticated();
    http.authorizeRequests().antMatchers(KEY_RESULTS_URI + "/**").authenticated();
    http.addFilterBefore(
        new CustomAuthorizationFilter(userService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
