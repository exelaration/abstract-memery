package com.exelaration.abstractmemery.security;

import static com.exelaration.abstractmemery.constants.SecurityConstants.DIRECT_MEME_LINK;
import static com.exelaration.abstractmemery.constants.SecurityConstants.GALLERY_URL;
import static com.exelaration.abstractmemery.constants.SecurityConstants.SETTINGS_URL;
import static com.exelaration.abstractmemery.constants.SecurityConstants.SIGN_UP_URL;

import com.exelaration.abstractmemery.filters.JWTAuthenticationFilter;
import com.exelaration.abstractmemery.filters.JWTAuthorizationFilter;
import com.exelaration.abstractmemery.repositories.UserRepository;
import com.exelaration.abstractmemery.services.implementations.UserDetailsServiceImpl;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  @Autowired private UserDetailsServiceImpl userDetailsService;
  @Autowired private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurity(
      UserDetailsServiceImpl userDetailsService,
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, GALLERY_URL, DIRECT_MEME_LINK)
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, SIGN_UP_URL)
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.PUT, SETTINGS_URL)
        .authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), userRepository))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
        // this disables session creation on Spring Security
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
    configuration.setAllowCredentials(true);
    configuration.setExposedHeaders(Arrays.asList("Authorization"));
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
