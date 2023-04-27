package com.example.scrumtrial.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Securitycfg {
    // TODO : change to UserDetailsManager
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder pwdEncoder){
        UserDetails usr = User.withUsername("admin")
                .password(pwdEncoder.encode("pwd"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(usr);
    }

    // TODO: fix security filters
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable().authorizeRequests((auth) -> auth
                .antMatchers("registration/*").permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder pwdEncoder(){
        return new BCryptPasswordEncoder();
    }
}
