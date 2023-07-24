package com.ms.products.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityAppConfig {

    public static final String PATTERN_ROUTE = "/products/**";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(HttpMethod.GET, PATTERN_ROUTE)
                        .hasRole(USER_ROLE).anyRequest().hasRole(ADMIN_ROLE));

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception{
        UserDetails user = User.builder()
                .username("mariana")
                .password(passwordEncoder().encode("12345"))
                .roles(USER_ROLE)
                .build();
        UserDetails admin = User.builder()
                .username("rian")
                .password(passwordEncoder().encode("12345"))
                .roles(USER_ROLE, ADMIN_ROLE)
                .build();

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
