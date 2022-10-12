package org.aibles.okr3.filter;

import static org.aibles.okr3.constants.ApiConstants.LOGIN_URI;
import static org.aibles.okr3.constants.ApiConstants.REFRESH_TOKEN_URI;
import static org.aibles.okr3.constants.ApiConstants.USERS_API_URI;
import static org.aibles.okr3.constants.CommonConstants.BEARER_TOKEN_INDEX;
import static org.aibles.okr3.constants.CommonConstants.START_OF_BEARER_TOKEN;
import static org.aibles.okr3.filter.FilterExceptionHandle.handle;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okr3.exception.UnauthorizedException;
import org.aibles.okr3.service.UserService;
import org.aibles.okr3.utils.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().equals(LOGIN_URI)
        || (request.getMethod().equals("POST") && request.getServletPath().equals(USERS_API_URI))
        || request.getServletPath().equals(REFRESH_TOKEN_URI)) {
      filterChain.doFilter(request, response);
    } else {
      log.info("(doFilterInternal)header : {}", request.getHeader("Authorization"));
      String authorizationHeader = request.getHeader("Authorization");
      String token = null;
      String email = null;
      try {
        if (authorizationHeader != null && authorizationHeader.startsWith(START_OF_BEARER_TOKEN)) {
          log.info("(doFilterInternal)header : {}", authorizationHeader);
          token = authorizationHeader.substring(BEARER_TOKEN_INDEX);
          email = JWTUtil.getInstance().getEmailFromToken(token);
          if (email != null) {
            log.info("(doFilterInternal)email : {}", email);
            UserDetails userDetails = userService.loadUserByUsername(email);
            if (JWTUtil.getInstance().validateToken(token, userDetails)) {
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                  new UsernamePasswordAuthenticationToken(
                      email, null, userDetails.getAuthorities());

              usernamePasswordAuthenticationToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext()
                  .setAuthentication(usernamePasswordAuthenticationToken);
              filterChain.doFilter(request, response);
            }
          }
        } else {
          throw new UnauthorizedException();
        }
      } catch (Exception e) {
        log.error("(doFilterInternal)exception : {}", e.getClass().getName());
        handle(response, e);
      }
    }
  }
}
